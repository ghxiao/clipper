#!/bin/sh

clipper_cli=$CLIPPER_HOME/clipper.sh
owlgres_rewrite=$OWLGRESROOT/sh/queryRewrite

dir=/Users/xiao/Dropbox/Projects/clipper/clipper-cli/src/test/resources/lubm-ex-20/query

#DB_NAME=owlgres_modlubm
DB_NAME=owlgres_modlubm_1_hole_20
DB_USER=xiao
DB_PASSWD=1234

TEMPLATE1=$(cat <<EOF 
PREFIX ub:<http://swat.cse.lehigh.edu/onto/univ-bench.owl#>
SELECT ?X
WHERE {  
	?X a ub:%s .
}
EOF
)

TEMPLATE2=$(cat <<EOF 
PREFIX ub:<http://swat.cse.lehigh.edu/onto/univ-bench.owl#>
SELECT ?X ?Y
WHERE {  
	?X ub:%s ?Y.
}
EOF
)

ontology_file=$dir/LUBM-ex-20.owl

sparql_file=$dir/q6.sparql

rew_dlv_file=`echo $sparql_file | sed "s/\.sparql$/\.rew\.dlv/g"`

${clipper_cli} -verbose=0 rewrite  $ontology_file -sparql=$sparql_file -name=FRAGMENT -output-datalog=$rew_dlv_file

rew_rules=$(cat $rew_dlv_file | grep '^ans(')

function create_view_for_body_atoms(){
	# body_atoms is a list of 'pred_name:arity'
    body_atoms=$(cat $rew_dlv_file  | \
        # filter the rows with ans(...) 
	    grep '^ans' | \
        # extract all the atoms
	    grep -o -e '\([a-zA-Z0-9]\)\+([^)]*)' | \
        # exclude ans(...)
        grep -v '^ans(' | \
        sed 's/(X[0-9]*/(X/g' | sed 's/,X[0-9]*/,Y/g' | \
        sed 's/([A-Z])/:1/g' | \
        sed 's/([A-Z],[A-Z])/:2/g' | \
        sort | uniq)

# CREATE VIEW for each body atom

    for atom in $body_atoms; do
        tmp=(${atom//:/ }) 
        name=${tmp[0]}
        arity=${tmp[1]}
        printf "%s/%s   " $name $arity
        case $arity in
            1 ) printf "$TEMPLATE1 \n" $name > $name.sparql
                ;;
            2 ) printf "$TEMPLATE2 \n" $name > $name.sparql
                ;;
        esac

        echo "CREATE OR REPLACE VIEW v_$name AS " > v_$name.sql

		# # call owlgres queryRewrite
        # $owlgres_rewrite --query $name.sparql --viewname v_$name --viewcols $arity \
        #     --db $DB_NAME --user $DB_USER --passwd "$DB_PASSWD" --shcemas public | \
        # 	# remove the header line like "Query reformulation produced 24 queries"
        #     grep -v "Query reformulation" | \
        #     # For object property, change output of att1 and att2 to
        #     # ids. 
		#     # for some strange reasons, in the output, the order of
        #     # name_1 and name_2 is switched, we have to walk around it
        #     sed 's/SELECT name_0.name AS x1, name_1.name AS x2/SELECT name_1.id AS att1, name_0.id AS att2, name_1.name AS x1, name_0.name AS x2/g' | \
        #     # For concept, change output of att1 to id
        #     sed 's/^SELECT name_0.name AS x1$/SELECT name_0.id AS att1, name_0.name AS name/g' \
        #     >> v_$name.sql

        $owlgres_rewrite --query $name.sparql --viewname v_$name --viewcols $arity \
            --db $DB_NAME --user $DB_USER --passwd "$DB_PASSWD" --shcemas public | \
        	# remove the header line like "Query reformulation produced 24 queries"
            grep -v "Query reformulation"  | \
            # For object property, change output of att1 and att2 to
            # ids. 
		    # for some strange reasons, in the output, the order of
            # name_1 and name_2 is switched, we have to walk around it
            # sed 's/SELECT name_0.name AS x1, name_1.name AS x2/SELECT name_1.id AS att1, name_0.id AS att2, name_1.name AS x1, name_0.name AS x2/g' | \
            # For concept, change output of att1 to id
            sed 's/SELECT name_0.name AS x1, name_1.name AS x2/SELECT innerRel.x1 AS att1, innerRel.x0 AS att2/g' | \
            sed 's/^SELECT name_0.name AS x1$/SELECT innerRel.x1 AS att1/g' | \
            sed 's/) as innerRel , individual_name name_0, individual_name name_1/) as innerRel/g' | \
            sed 's/) as innerRel , individual_name name_0/) as innerRel/g' | \
            grep -v "WHERE  innerRel.x1=name_0.id" | \
            grep -v "AND innerRel.x2=name_1.id" \
           >> v_$name.sql

        echo $name.sparql " -> " v_$name.sql
    done
}

function create_vieww_for_rew_query () {
    rew_rules=$(cat $rew_dlv_file | grep '^ans' )

    IFS_OLD=$IFS
    IFS="
"

    for rule in $rew_rules 
    do
        echo $rule
    done

    IFS=$IFS_OLD
}


create_view_for_body_atoms # $rew_dlv_file $DB_NAME $DB_USER
                           # $DB_PASSWD

#create_vieww_for_rew_query

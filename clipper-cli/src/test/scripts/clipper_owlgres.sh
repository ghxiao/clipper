#!/bin/sh

clipper_cli=$CLIPPER_HOME/clipper.sh
owlgres_rewrite=$OWLGRESROOT/sh/queryRewrite

dir=/Users/xiao/Dropbox/Projects/clipper/clipper-cli/src/test/resources/lubm-ex-20/query

DB_NAME=owlgres_modlubm
DB_USER=xiao
DB_PASSWD=

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

# echo $rew_rules

# cat $rew_dlv_file | grep '^ans' | grep -o -e '\([a-zA-Z0-9]\)\+([^)]*)' | sort

body_atoms=$(cat $rew_dlv_file | grep '^ans' )


#
# create_view_for_body_atoms($rew_dlv_file, $DB_NAME, $DB_USER,
# $DB_PASSWD)
# 
function create_view_for_body_atoms(){

#    rew_dlv_file=$1
#    DB_NAME=$2
#    DB_USER=$3
#    DB_PASSWD=$4

    body_atoms=$(cat $rew_dlv_file \
        | grep '^ans' \
        | grep -o -e '\([a-zA-Z0-9]\)\+([^)]*)' \
        | grep -v '^ans(' \
        | sed 's/(X[0-9]*/(X/g' | sed 's/,X[0-9]*/,Y/g' \
        | sed 's/(X)/:1/g' \
        | sed 's/(X,Y)/:2/g' \
        | sort | uniq)

#echo $body_atoms

# CREATE VIEW for each body atom

    for atom in $body_atoms; do
#    echo $atom
        tmp=(${atom//:/ }) 
        name=${tmp[0]}
        arity=${tmp[1]}
#    printf "%s/%s" $name $arity
        case $arity in
            1 ) printf "$TEMPLATE1 \n" $name > $name.sparql
                ;;
            2 ) printf "$TEMPLATE2 \n" $name > $name.sparql
                ;;
        esac

        echo "CREATE OR REPLACE VIEW v_$name AS " > v_$name.sql

		# for some strange reasons, in the output, the order of name_1 and name_2 is switched
		# so we have to fix this in the sed part
		# TODO: 
        $owlgres_rewrite --query $name.sparql --viewname v_$name --viewcols $arity \
            --db $DB_NAME --user $DB_USER --passwd "$DB_PASSWD" --shcemas public \
            | grep -v "Query reformulation" \
            | sed 's/SELECT name_0.name AS x1, name_1.name AS x2/SELECT name_1.id AS att1, name_0.id AS att2, name_1.name AS x1, name_0.name AS x2/g' \
            | sed 's/^SELECT name_0.name AS x1$/SELECT name_0.id AS att1, name_0.name AS name/g' \
            >> v_$name.sql

#    echo "DROP VIEW IF EXISTS v_$name" | psql -d $DB_NAME -U $DB_USER
#        cat v_$name.sql | psql -d $DB_NAME -U $DB_USER

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

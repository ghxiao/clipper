ONTOLOGIES=`ls -d ontology*`

OURREASONERCOMMAND=../../executableVersion/ourReasoner.sh
KAON2COMMAND=../../kaon2dlp/kaon2dlp 

test:
	for onto in ${ONTOLOGIES} ; do \
		cd $$onto ; \
		make -f ../tbox-Makefile test ; \
		cd .. ; \
	done 

clean:
	for onto in $(ONTOLOGIES) ;	do \
		cd $$onto ; \
		make -f ../tbox-Makefile clean ; \
		cd .. ; \
	done 
OURREASONERCOMMAND = cat
KAONCOMMAND = cat
# it is assumed the commands output to standard output

DLVCOMMAND=cat
#should be the actual dlv command

test : a.timing b.timing

translate: a.aboxtranslation.dl b.aboxtranslation.dl a.tboxtranslation.dl b.tboxtranslation.dl 

a.aboxtranslation.dl : abox.owl 
	 $(OURREASONERCOMMAND) abox.owl > a.aboxtranslation.dl

b.aboxtranslation.dl : abox.owl
	 $(KAONCOMMAND) abox.owl > b.aboxtranslation.dl

%.tboxtranslation.dl : 
	 cd ../;make $*.tboxtranslation.dl ;

%.timing : %.aboxtranslation.dl  %.tboxtranslation.dl
	 time -o $@ $(DLVCOMMAND) ../$*.tboxtranslation.dl $*.aboxtranslation.dl


clean :
	rm -rf *.timing *.dl 



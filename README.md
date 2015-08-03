# Introduction 

Clipper is a Reasoner for conjunctive query answering over Horn-SHIQ ontology via query rewriting. 

# Build from source 
We use [maven build system](http://maven.apache.org) and [Git](http://git-scm.com) to manage the source code.

	  $ git clone https://github.com/ghxiao/clipper.git clipper
	  $ cd clipper
	  $ ./mvn_build.sh

  
# Usage from CLI

1. Download [DLV](http://www.dlvsystem.com/dlvsystem/index.php/DLV) to your local machine (e.g., to `~/bin/dlv`).

2. Run `clipper.sh` from the generated package.

```
Usage: clipper.sh [options] [command] [command options]
  Options:
    -v, -verbose   Level of verbosity
                   Default: 1
  Commands:
    query      answerting conjunctive query
      Usage: query [options] <ontology.owl> -sparql <cq.sparql>    
        Options:
          -f, --output-format   output format, possible values: { table | csv |
                                atoms | html }
                                Default: table
          -dlv                  the location of dlv (e.g. /usr/local/bin/dlv)

    rewrite      rewrite the query w.r.t. the ontology, and generate a datalog program
      Usage: rewrite [options] <ontology.owl> [ -sparql <cq.sparql> ]       
        Options:
          --abox-only, -a             only rewrite ABox
                                      Default: false
          --ontology-and-query, -oq   rewrite ontology (= TBox + ABox) and query
                                      Default: false
          --ontology-only, -o         only rewrite ontology (= TBox + ABox)
                                      Default: false
          --remove-redundancy, -r     remove redundancy rules w.r.t the query
                                      Default: false
          --tbox-and-query, -tq       only rewrite TBox and query
                                      Default: false
          --tbox-only, -t             only rewrite TBox
                                      Default: false
          -output-datalog, -d         output datalog file 

    help      Print the usage
      Usage: help [options]
```
	  
# Development in Eclipse


1. Download the souce code via git.
2. In eclipse, import the project by "File" -> "Import" -> "Maven" ->
   "Existing Maven Projects" and point to Root directory of clipper

# References

[1] Thomas Eiter, Magdalena Ortiz, Mantas Šimkus, Trung-Kien Tran, and Guohui Xiao. 
Query Rewriting for Horn-SHIQ plus Rules. In
_Proceedings of the Twenty-Sixth AAAI Conference on Artificial
Intelligence (AAAI 2012), July 22-26, 2012, Toronto, Ontario, Canada_.
AAAI, AAAI Press, 2012.. [pdf](http://www.kr.tuwien.ac.at/staff/xiao/pub/2012/eostx2012-aaai-hshiq.pdf)

[2] Thomas Eiter, Magdalena Ortiz, Mantas Šimkus, Trung-Kien Tran, and
Guohui Xiao. Towards Practical Query Answering for Horn SHIQ. T.
In Y. Kazakov, D. Lembo, and F.
Wolter, editors, _Proceedings of the 2012 International Workshop on
Description Logics, DL-2012, Rome, Italy_, June 7-10, 2012, volume 846
of CEUR Workshop Proceedings. CEUR-WS.org, 2012. [pdf](http://www.kr.tuwien.ac.at/staff/xiao/pub/2012/eostx2012-dl-hshiq.pdf)




## Links 

* [Clipper @ Github](https://github.com/ghxiao/clipper)
* [Clipper @ TUWien](http://www.kr.tuwien.ac.at/research/systems/clipper/index.html)

Contact: xiao(a)inf.unibz.it
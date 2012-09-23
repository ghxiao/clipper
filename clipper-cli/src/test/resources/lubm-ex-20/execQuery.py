import os

univs = 100
ndeparts = 15
q = 2 

shcmd = r'''
dlv -filter=ans
'''

shcmd += "q" + q + ".dlv "

for univ in range(0:univs):
shcmd += '''
 q2.dlv LUBM-ex-20.owl.dl University0_0.owl.dl University0_1.owl.dl University0_2.owl.dl \
University0_3.owl.dl University0_4.owl.dl University0_5.owl.dl  University0_6.owl.dl \
University0_7.owl.dl University0_8.owl.dl University0_9.owl.dl  University0_10.owl.dl \
University0_7.owl.dl University0_8.owl.dl University0_9.owl.dl  University0_10.owl.dl \
University0_11.owl.dl University0_12.owl.dl University0_13.owl.dl  University0_14.owl.dl \
University0_7.owl.dl University0_8.owl.dl University0_9.owl.dl  University0_10.owl.dl \ 
'''

print shcmd

#os.system(shcmd)
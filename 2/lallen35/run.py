import os

nnetcmd = "java project2.NeuralNetTest spambase.arff "

def best_hidden_nodes():
    #preliminary test to see the number of hidden nodes that perform best at 500 iterations
    for i in xrange(0, 50):
        print "hidden nodes: ", i
        print nnetcmd + "rhc " + str(i) + " " + str(10000) + " > o-rhc-" + str(i)
        os.system(nnetcmd + "rhc " + str(i) + " " + str(10000) + " > o-rhc-" + str(i))

def best_cooling():
    for i in xrange(10,16):
        for j in xrange(1,10):
            zeros = ""
            for a in xrange(0,i):
                zeros = zeros + "0"
            sa_cmd = nnetcmd + "sa 6 10000 1" + zeros + " " + str(float(100 - j*5)/100) + " > o-sa-" + str(i) + "-" + str(100 - j*5)
            os.system(sa_cmd)

def best_genetic():
    for s in xrange(1,5):
        for ma in xrange(1,5):
            for mu in xrange(1,5):
                ga_cmd = nnetcmd + "ga 6 10000 " + str(s * 100) + " " + str(ma * 3) + " " + str(mu * 3) + " > o-ga-"
                ga_cmd = ga_cmd + str(s * 100) + "-" + str(ma * 3) + "-" + str(mu * 3)
                os.system(ga_cmd)



if __name__ == '__main__':
    best_genetic()

import scipy.integrate as sci
import numpy as np
import math
import matplotlib.pyplot as plt
#循环次数
times = 1000
#时间变量
tf = 0.00001
#初始化
x_list = [0,0,0,0,0,0]
listx = [x_list[0]]
listy = [x_list[1]]
#listx = []
#listy = []
#前轮转角
r = 0.6
#参数变量
Cf = 72276*2
Cr = 42487*2
a = 1.1
b = 1.67
m = 1370
Iz = 2315.3
Is = 16.68
U = 8
#公式
#plt.pot(x_list[0],x_list[1])
for num in range(0,times):
    dv = -((Cf+Cr)/(m*U))*x_list[3]-((a*Cf-b*Cr)/(m*U)+U)*x_list[4]+Cf/m*r
    dw = (b*Cr-a*Cf)/(Iz*U)*x_list[3]-(a*a*Cf+b*b*Cr)/(Iz*U)*x_list[4]+(a*Cf)/Iz*r
    v = dv*tf+x_list[3]
    w = dw*tf+x_list[4]
    df = w
    f = df*tf+x_list[2]
    dx = U*math.cos(f)-v*math.sin(f)
    dy = U*math.sin(f)+v*math.cos(f)
    x = dx*tf+x_list[0]
    y = dy*tf+x_list[1]
    x_list[0] = x
    x_list[1] = y
    x_list[2] = f
    x_list[3] = v
    x_list[4] = w
    x_list[5] = (num+1)*0.001
    listx.append(x)
    listy.append(y)
    print(x_list)
    #plt.plot(x,y)
#print(listx)
#print(listy)
#del listx[0]
#del listy[0]
plt.plot(listx,listy) 
plt.show()
#x = np.array([0,2])
#y1 = fi(x)
#I1 = sci.simps(y1,x)
#print (I1)
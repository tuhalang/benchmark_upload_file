import matplotlib.pyplot as plt

f_grpc = open('grpc.log', 'r')
f_node = open('node.log', 'r')
f_java = open('java.log', 'r')

grpc = []
for line in f_grpc:
    grpc.append(int(line.split(':')[-1]))

node = []
for line in f_node:
    node.append(int(line.split(':')[-1]))

java = []
for line in f_java:
    java.append(int(line.split(':')[-1]))

print(node)
print(java)
print(grpc)
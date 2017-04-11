
### 动态代理
```
代理实现流程：
1、书写代理类和代理方法，在代理方法中实现代理Proxy.newProxyInstance
2、代理中需要的参数分别为：被代理的类的类加载器soneObjectclass.getClassLoader()，
被代理类的所有实现接口new Class[] { Interface.class }，句柄方法new InvocationHandler()
3、在句柄方法中复写invoke方法，invoke方法的输入有3个参数Object proxy（代理类对象）,
 Method method（被代理类的方法）,Object[] args（被代理类方法的传入参数），在这个方法中，
 我们可以定制化的开发新的业务。
4、获取代理类，强转成被代理的接口
5、最后，我们可以像没被代理一样，调用接口的认可方法，方法被调用后，方法名和参数列表将被传入代理类的invoke方法中，
进行新业务的逻辑流程。
```

```
Boss
BossImpl
ProxyBoss
SaleActionMain
------------------
Person
PersonImpl
ProxyPerson
------------------
ProxyList
```




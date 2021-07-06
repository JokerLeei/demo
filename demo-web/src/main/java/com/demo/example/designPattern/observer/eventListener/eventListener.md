### Spring中使用@Async完成方法的异步事件调用
在这里面，我们涉及到三个主要对象：事件发布者、事件源、事件监听器。根据这三个对象，我们来配置我们的注册事件实例：
1.定义一个事件
在spring中，所有事件都必须扩展抽象类ApplicationEvent,同时将事件源作为构造函数参数,这样在发布的事件的时候才能把数据传输过来.

```java
/**
 * 定义事件
 */
public class AccessEvent extends ApplicationEvent{
	/**
	 * 定义发布事件时需要传输的数据作为成员变量
	 */
	private Request req;
 
	public AccessEvent(Object source, Request req) {
		super(source);	// //source字面意思是根源，意指发送事件的根源，即我们的事件发布者
		this.req = req;
	}
 
	public Request getReq() {
		return req;
	}
 
}
```
 2.定义事件的发布者
事件发送的代表类是ApplicationEventPublisher,所以我们的事件发布者需要定义成员属性ApplicationEventPublisher来发布我们的事件.
```java
/**
 * 定义事件的发布者
 */
public class BaseController {
	
	/**
	 * 注入ApplicationEventPublisher用来发布事件
	 */
	@Resource
    private ApplicationEventPublisher publisher;
 
    @ModelAttribute	//被 @ModelAttribute 注解的方法会在Controller每个方法执行之前都执行
    public void accessValidate(@RequestBody Request req, HttpServletRequest request, HttpServletResponse response) {
		//发布任务,并传递事件源
		publisher.publishEvent(new AccessEvent(this, req));
		System.out.println("执行原有的程序" + LocalDateTime.now());
    }
 
}
```
3.定义事件的监听者
通过@EventListener监听事件,通过 @Async进行异步调用,(这里也可以只用其他方式,只是这样最简单.其他方式为:事件监听类需要实现我们的ApplicationListener接口，除了可以实现ApplicationListener定义事件监听器外，我们还可以让事件监听类实现SmartApplicationListener（智能监听器）接口，。关于它的具体用法和实现可参考我的下一篇文章《spring学习笔记(14)趣谈spring 事件机制[2]：多监听器流水线式顺序处理 》。而此外，如果我们事件监听器监听的事件类型唯一的话，我们可以通过泛型来简化配置。)
```java
/**
 * 定义事件的监听器
 */
@Component
@EnableAsync
public class AccessEventListener {
	
	@EventListener
    @Async
	public void addAccess(AccessEvent accessEvent){
		Request req = accessEvent.getReq();
		 try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("向redis中存数据"+new Date().toLocaleString());
	}
}
```
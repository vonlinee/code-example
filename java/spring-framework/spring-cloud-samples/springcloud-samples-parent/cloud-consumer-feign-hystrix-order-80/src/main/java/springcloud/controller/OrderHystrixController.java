package springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import springcloud.service.PaymentHystrixService;

@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

	@Resource
	private PaymentHystrixService paymentHystrixService;

	@GetMapping("/consumer/payment/hystrix/ok/{id}")
	public String paymentInfo_OK(@PathVariable("id") Integer id) {
		String result = paymentHystrixService.paymentInfo_OK(id);
		return result;
	}

	@GetMapping("/consumer/payment/hystrix/timeout/{id}")
	public String paymentInfo_TimeOut2(@PathVariable("id") Integer id) {
		String result = paymentHystrixService.paymentInfo_TimeOut(id);
		return result;
	}

	@GetMapping("/consumer/payment/hystrix/timeout/{id}")
	@HystrixCommand(fallbackMethod = "PaymentTimeOutFallbackMethod", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500") })
	public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
		int age = 10 / 0;
		String result = paymentHystrixService.paymentInfo_TimeOut(id);
		return result;
	}

	@GetMapping("/consumer/payment/hystrix/timeout1/{id}")
	@HystrixCommand
	public String paymentInfo_TimeOut1(@PathVariable("id") Integer id) {
		int age = 10 / 0;
		String result = paymentHystrixService.paymentInfo_TimeOut(id);
		return result;
	}

	public String PaymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
		return "我是消费者80，对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己，o(╥﹏╥)o";
	}

	// 下面是全局fallback方法
	public String payment_Global_FallbackMethod() {
		return "Global异常处理信息，请稍后再试，o(╥﹏╥)o";
	}
}

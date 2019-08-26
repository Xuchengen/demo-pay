# 支付演示

<p align="center">
	<a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.html">
		<img src="https://img.shields.io/:license-apache-blue.svg"/>
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
	</a>
	<a target="_blank" href="https://travis-ci.org/Xuchengen/demo-pay">
		<img src="https://travis-ci.org/Xuchengen/demo-pay.svg?branch=master"/>
	</a>
	<a target="_blank" href='https://github.com/Xuchengen/demo-pay'>
		<img src="https://img.shields.io/github/stars/Xuchengen/demo-pay?style=social" alt="github star"/>
	</a>
</p>

## 简介
支付演示是一个集成支付宝、微信、银联云闪付等第三方支付系统接口的一个集成环境便于一些新手开发者快速学习和测试。

## 关于条码支付(被扫模式)
在工程测试包内提供了一个简易的条码扫描枪模拟器。利用投屏软件将手机客户端收款码界面投射到桌面上，使用模拟扫描器识别二维码/条形码然后创建订单发起支付动作进行扣款。

<img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/scan_tool.jpg" width="50%" alt="条码扫描枪模拟器">

## 开发状态
支付演示系统仍然处于开发阶段，现阶段已经集成支付宝、银联。

## 支付接口申请
支付宝目前支持个人申请当面付收单接口，费率为0.6。

提供一张可以申请支付宝当面付的商户门头照

<img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/apply.jpg" width="50%" alt="商户门头照">

## 特别鸣谢
* <a href="https://github.com/looly/hutool">Hutool</a>

* <a href="http://ibeetl.com/">Beetl</a>

以上排名不分先后。
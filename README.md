# 支付演示

<a target="_blank" href="https://raw.githubusercontent.com/Xuchengen/demo-pay/master/LICENSE">
    <img src="https://img.shields.io/:license-MIT-blue.svg"/>
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

<p align="center">
    <a href="https://www.jetbrains.com/?from=X-UnionPay" target="_blank">
        <img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/jetbrains.svg" width="30%" alt="Jetbrains">
    </a>
</p>

## 简介
支付演示是一个基于SpringBoot2.0框架开发的WEB系统，目前已集成支付宝、微信、银联等第三方支付系统接口，目的是方便从事支付业务相关开发者学习与交流。

如果您觉得该项目对您有所帮助那么请给与Star或Fork，欢迎吐槽。

## 关于条码支付(被扫模式)
在工程测试包内提供了一个简易的条码扫描枪模拟器。利用投屏软件将手机客户端收款码界面投射到桌面上，使用模拟扫描器识别二维码/条形码然后创建订单发起支付动作进行扣款。

<img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/scan_tool.jpg" width="50%" alt="条码扫描枪模拟器">

## 开发状态
支付演示系统仍然处于开发阶段，现阶段已经集成支付宝、银联。

## 支付接口申请
支付宝目前仅支持个人申请当面付收单接口，费率为0.6%。

提供一张可以申请支付宝当面付的商户门头照

<img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/apply.jpg" width="50%" alt="商户门头照">

## 特别鸣谢
* <a href="https://github.com/looly/hutool" title="一套保持Java甜蜜的工具">Hutool——一套保持Java甜蜜的工具</a>

* <a href="http://ibeetl.com/" title="Beetl国产高性能Java模板引擎">Beetl——国产高性能Java模板引擎</a>

* <a href="https://github.com/Javen205/IJPay" title="IJPay让支付触手可及">IJPay——IJPay让支付触手可及</a>

* <a href="https://www.jetbrains.com/?from=X-UnionPay" title="专业人士和团队的开发人员工具">Jetbrains——专业人士和团队的开发人员工具</a>

以上排名不分先后。

## 友情捐赠
您的捐赠将用于购买JetBrains旗下IDEA开发工具正版序列号，并进行共享。
<p align="center">
    <a href="https://github.com/Xuchengen/demo-pay/blob/master/asset/unionpay.jpeg" target="_blank">
        <img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/unionpay.jpeg" width="30%" alt="银联支付">
    </a>
    <a href="https://github.com/Xuchengen/demo-pay/blob/master/asset/wechat.jpeg" target="_blank">
        <img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/wechat.jpeg" width="30%" alt="微信支付">
    </a>
    <a href="https://github.com/Xuchengen/demo-pay/blob/master/asset/alipay.jpeg" target="_blank">
        <img src="https://github.com/Xuchengen/demo-pay/blob/master/asset/alipay.jpeg" width="30%" alt="支付宝">
    </a>
</p>
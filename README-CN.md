# HiPermission

一个简单易用的漂亮权限申请库

# 概述
随着Android6.0的普及,权限申请也变成了我们开发中必写的一段代码。比如sd卡权限、定位权限、拍照权限，这些几乎都是每个app必备的。而一般情况下我们都会在需要权限之前申请，然后各种判断。那既然是一些必备的是权限，我们为何不在我们一次打开app的时候就把这个全部都申请了呢？可是一下申请那么多权限对用户来说显然不是很友好，所以我们不妨在申请权限之前告诉一下用户，好让用户有个心理准备。于是借鉴了饿了么的权限申请界面，封装了一个库

# 效果

![效果图](http://upload-images.jianshu.io/upload_images/1643415-aeb919fdcddc1499.gif?imageMogr2/auto-orient/strip)

# 过程

在申请权限的过程中难免会遭到用户无情的拒绝，那么就会弹出提示告诉用户这是必备的，你必须得同意！如果用户再一次手抖选择拒绝，那么我们会指引用户去权限管理里面打开我们的权限，如果还是拒绝那么..我特么真没辙了！！！

# 使用

Gradle:

	compile 'me.weyye.hipermission:library:1.0.7'

Or Maven:

	<dependency>
	  <groupId>me.weyye.hipermission</groupId>
	  <artifactId>library</artifactId>
	  <version>1.0.7</version>
	  <type>pom</type>
	</dependency>

一行代码搞定

``` java
HiPermission.create(context)
	.checkMutiPermission(new PermissionCallback() {
		@Override
		public void onClose() {
			Log.i(TAG, "onClose");
			showToast("用户关闭权限申请");
		}

		@Override
		public void onFinish() {
			showToast("所有权限申请完成");
		}

		@Override
		public void onDeny(String permission, int position) {
			Log.i(TAG, "onDeny");
		}

		@Override
		public void onGuarantee(String permission, int position) {
			Log.i(TAG, "onGuarantee");
		}
	});
```

就这样轻松搞定三个必备的权限

你想申请别的权限？那也没问题

``` java
List<PermissionItem> permissionItems = new ArrayList<PermissionItem>();
permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_memory));
permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
HiPermission.create(MainActivity.this)
			.permissions(permissionItems)
			.checkMutiPermission(...);
```

## 自定义主题

什么？想改下提示信息？界面不符合你的主题颜色？so easy

``` java
HiPermission.create(MainActivity.this)
			.title("亲爱的上帝")
			.permissions(permissionItems)
			.filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))//图标的颜色
			.msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
			.style(R.style.PermissionBlueStyle)
			.checkMutiPermission(...);
```

> 设置主题后一定要调用`filterColor()`,否者权限图标会变成默认的黑色


styles.xml

``` xml
    <style name="PermissionBlueStyle">
        <item name="PermissionTitleColor">@color/colorPrimaryDark</item>
        <item name="PermissionMsgColor">@color/colorPrimary</item>
        <item name="PermissionItemTextColor">@color/colorPrimary</item>
        <item name="PermissionButtonBackground">@drawable/shape_btn</item>
        <item name="PermissionBackround">@drawable/shape_bg_white</item>
        <item name="PermissionButtonTextColor">@android:color/white</item>
    </style>
```


![效果图](http://upload-images.jianshu.io/upload_images/1643415-f5fa64196c5bd5f6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 默认图标
如果你需要申请其他权限，但是没有图标？`HiPermission`已经给你准备好啦~

| |日历|相机|联系人|定位|
|:-:|:-:|:-:|:-:|:-:|
| |![](http://upload-images.jianshu.io/upload_images/1643415-f64d7048c37dd8e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-1697d58118fce639.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-e897ebfaa200ad34.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-ee31c852a07475df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|
|drawableId|permission_ic_calendar|permission_ic_camera|permission_ic_contacts|permission_ic_location|


| |麦克风|手机|短信|存储|传感器|
|:-:|:-:|:-:|:-:|:-:|:-:|
| |![](http://upload-images.jianshu.io/upload_images/1643415-42be4b1f4d72c177.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-7dd3e979f0448ad5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-af7115c6855019f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-c21d7061a286192c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![](http://upload-images.jianshu.io/upload_images/1643415-9905653ae13b86e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|
|drawableId|permission_ic_micro_phone|permission_ic_phone|permission_ic_sms|permission_ic_storage|permission_ic_sensors|

使用图标

``` java
List<PermissionItem> permissions = new ArrayList<PermissionItem>();
//使用图标
permissions.add(new PermissionItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
HiPermission.create(MainActivity.this)
		.permissions(permissions)
		.style(R.style.PermissionDefaultGreenStyle)
		.checkMutiPermission(...);
```

>图标默认是黑色，需要调用`filterColor()`更改图标颜色

## 默认主题

当然，目前有3种默认的主题和动画

| |Screenshot| Screenshot|Screenshot|
|:-:|:-:|:-:| :-:|
| |![](http://upload-images.jianshu.io/upload_images/1643415-9778484edc5c78a7.gif?imageMogr2/auto-orient/strip) |![](http://upload-images.jianshu.io/upload_images/1643415-f2eb04a9b1421357.gif?imageMogr2/auto-orient/strip)|![](http://upload-images.jianshu.io/upload_images/1643415-0a8990f176c5bb8f.gif?imageMogr2/auto-orient/strip)|
|styleId|PermissionDefaultNormalStyle|PermissionDefaultGreenStyle|PermissionDefaultBlueStyle|
|AnimId|PermissionAnimFade|PermissionAnimModal|PermissionAnimScale|

主题默认不带动画，需自己手动设置，像这样

``` java
HiPermission.create(MainActivity.this)
                        .title(getString(R.string.permission_cus_title))
                        .permissions(permissions)
                        .msg(getString(R.string.permission_cus_msg))
                        .animStyle(R.style.PermissionAnimModal)//设置动画
                        .style(R.style.PermissionDefaultGreenStyle)//设置主题
                        .checkMutiPermission(...);
```

如果你想改style的个别属性，你可以在你的style里面继承某个style重写某个属性，像这样

``` xml
    <style name="CusStyle" parent="PermissionDefaultGreenStyle">
        <item name="PermissionBgFilterColor">#75D175</item>
    </style>
```

效果

![](http://upload-images.jianshu.io/upload_images/1643415-4dc09678be25a146.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


以下是每个属性的解释

| 属性名        | 类型           | 解释 |
| ------------- |:-------------:| -----:|
| PermissionTitleColor     | int | 标题文字颜色 |
| PermissionMsgColor| int | 描述文字颜色 |
| PermissionItemTextColor| int | 权限文字颜色 |
| PermissionButtonTextColor| int | 按钮文字颜色 |
| PermissionButtonBackground| drawable| 按钮背景 |
| PermissionBackround| drawable| 对话框背景 |
| PermissionBgFilterColor| int| 背景过滤色 |
| PermissionIconFilterColor| int|图标颜色 |

如果设置主题后不想调用`filterColor()`可在你的style里面添加`PermissionIconFilterColor`属性

# 注意

> 清单文件一定要注册申请的权限，不然申请后会默认拒绝，而且设置-权限管理界面也无法看到此权限

以下原因不会弹框

* 6.0以下版本(系统自动申请)
* 暂时发现vivo、oppo、魅族的6.0以上版本

>因为这些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了。也就是说这些系统会跟以前 6.0 以下的版本一样，需要用到权限的时候系统会自动申请，就算我们主动申请也是没用的（我拿饿了么测试也没弹出权限框）

# 最后
如果这个对你有用，不妨来个star？

# 联系我

* QQ:505141450
* Email:[hiweyye@gmail.com](mailto:hiweyye@gmail.com)
* My Blog:[http://weyye.me](http://weyye.me)
* 简书:[http://www.jianshu.com/u/c5da2f9c87fb](http://www.jianshu.com/u/c5da2f9c87fb)
* CSDN:[http://blog.csdn.net/yewei02538](http://blog.csdn.net/yewei02538)

# License
    Copyright (C) 2017 WeyYe

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

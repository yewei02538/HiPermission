# HiPermission
A simple and beautiful runtime permission library on Android.
[中文文档](http://www.jianshu.com/p/c83503f0384d)

# Features
* One line of code to solve
* Various logics are omitted
* Support change permission Ui

`ACCESS_FINE_LOCATION`,`WRITE_EXTERNAL_STORAGE` and `CAMERA`,these almost every app requires permissions ,so we can request these permissions when app launch.But it's not very friendly to request so many permissions at once.So before the request permission, we need to tell the user why we did this.The inspiration comes from [eleme](https://play.google.com/store/apps/details?id=me.ele)

# Screenshot

![](/screenshot/screenshot1.gif)

# Demo

[Download](/screenshot/app-debug.apk)

# Usage
Use Gradle:

	compile 'me.weyye.hipermission:library:1.0.1'

Or Maven:

	<dependency>
	  <groupId>me.weyye.hipermission</groupId>
	  <artifactId>library</artifactId>
	  <version>1.0.1</version>
	  <type>pom</type>
	</dependency>
  
  In your Activity or anywhere:
  
  They will request three necessary permissions：`CAMERA`,`ACCESS_FINE_LOCATION` and `WRITE_EXTERNAL_STORAGE`
  ``` java
HiPermission.create(context)
	.checkMutiPermission(new PermissionCallback() {
		@Override
		public void onClose() {
			Log.i(TAG, "onClose");
			showToast("They cancelled our request");
		}

		@Override
		public void onFinish() {
			showToast("All permissions requested completed");
		}

		@Override
		public void onDeny(String permisson, int position) {
			Log.i(TAG, "onDeny");
		}

		@Override
		public void onGuarantee(String permisson, int position) {
			Log.i(TAG, "onGuarantee");
		}
	});
```

Sometimes you don't need to request for these permissions,you can add permissions that you want to request like this

``` java
List<PermissonItem> permissonItems = new ArrayList<PermissonItem>();
permissonItems.add(new PermissonItem(Manifest.permission.CAMERA, "Camera", R.drawable.permission_ic_memory));
permissonItems.add(new PermissonItem(Manifest.permission.ACCESS_FINE_LOCATION, "Location", R.drawable.permission_ic_location));
HiPermission.create(MainActivity.this)
			.permissions(permissonItems)
			.checkMutiPermission(...);
```

Can I change the color of the interface? Or change hint information? Yes!

``` java
HiPermission.create(MainActivity.this)
			.title("Dear God")
			.permissions(permissonItems)
			.filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))//permission icon color
			.msg("To protect the peace of the world, open these permissions! You and I together save the world!")
			.style(R.style.PermissionBlueStyle)
			.checkMutiPermission(...);
```
> After you have set the theme, you must called `filterColor ()` to set the color of the icon,otherwise the default is black

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

![](/screenshot/screenshot2.jpg)


The following are all attributes


| attributes name       | type 
| ------------- |:-------------:|
| PermissionTitleColor     | int | 
| PermissionMsgColor| int |
| PermissionItemTextColor| int |
| PermissionButtonTextColor| int |
| PermissionButtonBackground| drawable|
| PermissionButtonTextColor| drawable|

# End
If you like,please give a star as an encouragement to me

# About me
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

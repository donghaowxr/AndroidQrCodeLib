# Android二维码工具库
Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	    ...
		maven { url 'https://jitpack.io' }
	}
}
```
```
implementation 'com.github.donghaowxr:AndroidQrCodeLib:1.0.1'
```
Kotlin Sample Code

生成二维码
```
val qrCode = EncodingManager.createQRCode("http://www.baidu.com", 300, (0xff000000).toInt())
iv_qrcode.setImageBitmap(qrCode)
```
生成带logo的二维码
```
var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
val qrCode = EncodingManager.createQRCodeWithLogo("http://www.baidu.com", 300, (0xff000000).toInt(), bitmap)
iv_qrcode.setImageBitmap(qrCode)
```
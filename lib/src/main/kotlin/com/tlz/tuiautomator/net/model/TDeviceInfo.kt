package com.tlz.tuiautomator.net.model

/**
 * 设备基本信息.
 * By tomlezen.
 * Date: 2019-08-02.
 * Time: 16:03.
 * @property currentPackageName String 当前界面应用包名
 * @property displayWidth Int 屏幕显示宽度
 * @property displayHeight Int 屏幕显示高度
 * @property displayRotation Int 屏幕旋转度数
 * @property displaySizeDpX Int x轴像素密度值
 * @property displaySizeDpY Int y轴橡树密度值
 * @property productName String 系统名
 * @property screenOn Boolean 当前是否是亮屏状态
 * @property sdkInt Int 系统sdk版本号
 * @property naturalOrientation Boolean 手机默认方向
 * @constructor
 */
data class TDeviceInfo(
    val currentPackageName: String,
    val displayWidth: Int,
    val displayHeight: Int,
    val displayRotation: Int,
    val displaySizeDpX: Int,
    val displaySizeDpY: Int,
    val productName: String,
    val screenOn: Boolean,
    val sdkInt: Int,
    val naturalOrientation: Boolean
)
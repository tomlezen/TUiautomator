# TUiautomator - uiautomator2的kotlin版本（仅供学习参考）

TUiautomator是根据自己需求，参照[uiautomator2](https://github.com/openatx/uiautomator2)翻译的kotlin版本，只翻译了常用、需求高的api
翻译的初衷是：

   1. uiautomator2是python语言开发，自己py语法不熟悉，用起来不是很顺手
   2. uiautomator2不能单独在手机运行，也就不能手动的开启和关闭脚本运行
   3. 可以用Uiautomator2开发app，完成自主控制运行

## 安装环境

1. 安装**uiautomator2**

   ```
   pip install --upgrade --pre uiautomator2
   ```

2. 安装程序到手机 确保adb已经添加到环境变量中，执行下面的命令会自动安装本库所需要的设备端程序：[uiautomator-server](https://github.com/openatx/android-uiautomator-server/releases) 、[atx-agent](https://github.com/openatx/atx-agent)、[openstf/minicap](https://github.com/openstf/minicap)、[openstf/minitouch](https://github.com/openstf/minitouch)

   ```
   # init 所有的已经连接到电脑的设备
   python -m uiautomator2 init
   ```

   有时候init也会出错，请参考[手动Init指南](https://github.com/openatx/uiautomator2/wiki/Manual-Init)

3. 【可选】安装[weditor](<https://github.com/openatx/weditor>) （个人推荐安装，因为在写程序时方便测试）

   ```
   pip install -U weditor
   ```

   命令行启动 `python -m weditor` 会自动打开浏览器，输入设备的ip或者序列号，点击Connect即可

以上的配置均可以参考[uiautomator2](https://github.com/openatx/uiautomator2)文档


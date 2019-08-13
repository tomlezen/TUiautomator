package com.tlz.tuiautomator

/**
 * 所有方法名.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 17:59.
 */
object TUiautomatorMethods {

    const val CLICK = "click"
    const val LONG_CLICK = "longClick"
    const val DOUBLE_CLICK = "doubleClick"
    const val SWIPE = "swipe"
    const val PRESS_KEY = "pressKey"
    const val SCROLL_TO = "scrollTo"
    const val SCROLL_TO_END = "scrollToEnd"
    const val SCROLL_FORWARD = "scrollForward"
    const val SCROLL_TO_BEGINNING = "scrollToBeginning"
    const val DRAG = "drag"
    const val INJECT_INPUT_EVENT = "injectInputEvent"
    const val EXIST = "exist"
    const val OBJ_INFO = "objInfo"
    const val WAIT_FOR_EXISTS = "waitForExists"
    const val WAIT_UNTIL_GONE = "waitUntilGone"

    object Toast{
        const val SHOW = "makeToast"
        const val RESET = "clearLastToast"
        const val GET_MESSAGE = "getLastToast"
    }
}
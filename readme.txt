
API Level     Target
   29		    10
   28		    9
   27		   8.1
   26		   8.0
   25		  7.1.1
   24		   7.0
   23		   6.0
   22		   5.1
   21		   5.0
   19		   4.4

###创建桌面快捷方式：三种


        #####1、静态快捷方式 （Static shortcuts）          版本>=25

                xml文件夹下shortcut标签中值的含义：

                    shortcutId:shortcut 唯一标识符，相同的 shortcutId 会被覆盖,必需字段
                    shortcutShortLabel:将 shortcut 拖动到桌面时显示的名字，官方建议不超过 10 个字符，必须字段
                    shortcutLongLabel:shortcut 列表中每个 shortcut 的名字，不宜过长，如果过长或未设置默认会显示 ShortLabel，官方建议不超过 25 个字符。可选字段。
                    icon: shortcut 的 icon，在列表展示和拖动到桌面时显示需要，可选字段
                    enabled:表示 shortcut 是否可用，false 表示禁用
                    shortcutDisabledMessage:为已固定在桌面的 shortcut 被 Disabled 后点击时的 Toast 提示内容。可选字段。
                    intent:为点击 shortcut 时响应的 Intent，必须字段。



                移除操作：

                    无法更改或移除，除非重新发版






        #####2、动态快捷方式 （Dynamic shortcuts）        版本>=25

                注意：不会被保存，也就是说每次打开app时都需要重新发布一下


                移除操作：

                    可以动态的更新、移除






        #####3、桌面快捷方式  （Pinned shortcuts）        版本>=26（25有这个类，但没有方法）


                Pinned这种快捷方式只能在版本>=26的设备上使用，对于25的版本，在shortcuts列表中可以长按，选中某一个shortcut，
                然后拖拽的桌面上

                移除操作：3种方式

                    * 用户主动从桌面移除
                    * 卸载应用
                    * 清除应用缓存

                更新方式同Dynamic shortcuts



### 系统设置更改产生的bug

        系统设置的更改，比如修改系统的语言，动态 Shortcuts 是不能动态更新的，此时需要创建广播监听 Intent.ACTION_LOCALE_CHANGED ,
        当收到广播时重新更新快捷方式，保证快捷方式展示没有问题

        这块比较复杂：目前没有找到比较好的办法,通过在sp中维护了一个map，记录了shortcut id和资源的对应关系（就是无法获取原先的资源id，比如说shortcutShortLabel）


### Shortcuts展示顺序


        先显示静态创建的后显示动态创建的

        在每种快捷方式中，又会按照 ShortcutInfo.getRank() 按等级递增的顺序排序。等级是非负的，连续的整数，
        调用 updateShortcuts(List)，addDynamicShortcuts(List) 或 setDynamicShortcuts(List) 时，可以
        更新现有快捷方式的等级。

        排名是自动调整的，因此它们对于每种类型的快捷方式都是唯一的。 例如，有三个 rank 分别为 0、1和2 的动态快捷方式，
        此时再添加一个 rank 为 1 的动态快捷方式放在第二的位置上，那最后两个就会被顺延一个位置，rank 变成 2和3。




### 最大支持快捷方式数

        * 最大支持创建5个，不包含Pinned shortcuts，Pinned shortcuts可以创建任意个

        示例：

        1、聊天应用程序发布四个动态快捷方式，表示最近的四个对话(c1,c2,c3,c4)
        2、用户将所有的快捷方式复制到桌面
        3、然后用户又启动三个额外的最近对话(c5,c6和c7)，这是重新发布更新动态快捷方式，那新的快捷方式列表为：c4,c5,c6,c7。改应用必须删除过 c1,c2喝c3，因为只能展示四个快捷方式，但是桌面已经保存的这三个快捷方式是可以正常访问的。
           用户现在其实可以总共访问七个快捷方式，其中包括四个动态快捷方式和桌面的三个快捷方式
        4、应用程序可以使用 updateShortcuts(List) 来更新上述七个任意快捷方式
        5、使用 addDynamicShortcuts() 和 setDynamicShortcuts() 同样可以更新具有相同 shortcutId 的快捷方式对象，但是他们不能跟新非动态的快捷方式。



### 注意事项

        * 为了更好的视觉效果，一般超链接不要多于四个

        * 只有在 shortcuts 的意义存在时才更新

          当改变动态快捷方式时，只有在 shortcut 仍然保持它的含义时，调用 updateShortcuts() 方法改变它的信息，否则，
          应该使用 addDynamicShortcuts() 或 setDynamicShortcuts() 创建一个具有新含义的 shortcutId 的快捷方式。

          例如，如果我们已经创建了导航到一个超市的快捷方式，如果超市的名称改变了但是位置并没有变化时，只更新信息是合适的，
          但是如果用户开始在一个不同位置的超市购物时，最好就是创建一个新的快捷方式。










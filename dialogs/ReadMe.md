###ReadMe
[wiki](http://confluence.inner.youdao.com/pages/viewpage.action?pageId=2537049)


####弹窗组件预计设计能力
> 按照 作业宝App UI稿设计，规划大致8种风格能力。
> * 8种风格 均为常见风格样式
> * 每种风格支持常见的基本数据输入


具体支持的风格：
- 风格一；[UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=4d457c79-1c3a-412b-a77f-ec81d9637fa3&project_id=4d457c79-1c3a-412b-a77f-ec81d9637fa3&image_id=87304d4f-b733-4c95-b628-8fc5a5d78bbb)
- 风格二；[UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&project_id=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&image_id=eb07292a-dc69-49b9-abb8-fa4f11fffb4d)
- 风格三；[UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&project_id=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&image_id=356f885f-dcd4-4cb2-beb3-5bea37cf1871)
- 风格四; [UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&project_id=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&image_id=dc48574c-9419-4b9c-9ba5-b327482844a5)
- 风格五；[UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&project_id=77f7dec9-0cc5-4168-8a62-fabd93eaacbb&image_id=b35a5093-8226-4cad-a773-bddd5671cd7e)
- 风格六；[UI设计稿链接](https://lanhuapp.com/web/#/item/board/detail?pid=5c1f1eff-92b4-4c02-a28f-422ec573f633&project_id=5c1f1eff-92b4-4c02-a28f-422ec573f633&image_id=50a2b27a-7335-42eb-be29-ab6bbbbdc553)
- 风格七，同风格六，区别是可以控制自动隐藏；
- 风格八，输入弹窗，参考昵称输入框；


####弹窗组件技术方案

    基于Activity实现常见的悬浮弹窗。脱离过重的MVP架构，便于移植，当然内部可以使用轻量的MVP等进行解耦。同时不要过于依赖开源库。


（其余待补充...）
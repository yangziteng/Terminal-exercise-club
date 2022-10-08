<!-- 社团招新 -->
<template>
    <view>
        <!-- 导航条 -->
        <view class="nav">
            <view class="nav_box">
                <view @tap="navSwitch" data-index="0" :class="navState == 0 ? 'nav-switch-style' : 'nav-switch-style-close'">全部活动</view>
                <view :class="navState == 0 ? 'bottomline' : 'bottomline_close'"></view>
            </view>
            <view class="nav_box">
                <view @tap="navSwitch" data-index="1" :class="navState == 1 ? 'nav-switch-style' : 'nav-switch-style-close'">我的活动</view>
                <view :class="navState == 1 ? 'bottomline' : 'bottomline_close'"></view>
            </view>
        </view>
        <!-- 滑动页 -->
        <swiper @change="bindchange" :current="navState" class="box" duration="500">
            <swiper-item class="swiperitem">
                <view class="style-roll">
                    <view class="showgroup_card">
                        <scroll-view class="scrollView" :scroll-y="true">
                            <view class="VerticalBox">
                                <!-- 左列导航 -->
                                <scroll-view class="VerticalNav nav2" scroll-y scroll-with-animation :scroll-top="VerticalNavTop" style="height: calc(100vh - 109rpx)">
                                    <view
                                        :class="'cu-item ' + (index == TabCur ? 'text-green cur' : '')"
                                        @tap="tabSelect"
                                        :data-id="index"
                                        style="line-height: unset; display: flex; align-items: center"
                                        v-for="(item, index) in mess_list"
                                        :key="index"
                                    >
                                        {{ item.association[0].detail }}
                                    </view>
                                </scroll-view>
                                <!-- 右列具体活动卡片 -->
                                <scroll-view
                                    class="VerticalMain"
                                    scroll-y
                                    scroll-with-animation
                                    style="height: calc(100vh - 109rpx)"
                                    :scroll-into-view="'main-' + MainCur"
                                    @scroll="VerticalMain"
                                >
                                    <view class="padding-top padding-lr" :id="'main-' + index" @tap="goBao" :data-index="index" v-for="(item, index) in mess_list" :key="index">
                                        <!-- 社团标题 -->

                                        <view class="cu-bar solid-bottom bg-white">
                                            <!-- <image style="width: 50rpx;height: 50rpx;border-radius: 50%;" src="https://ossweb-img.qq.com/images/lol/web201310/skin/big10001.jpg"></image> -->
                                            <view class="action" style="color: #383838">
                                                <text class="cuIcon-title text-green" style="color: #8b8b8b"></text>
                                                {{ item.association[0].detail }}
                                            </view>
                                        </view>

                                        <!-- 活动卡片 -->

                                        <view class="cu-list menu-avatar">
                                            <!-- 卡片一 -->
                                            <view class="cu-card dynamic">
                                                <view class="cu-item shadow">
                                                    <view class="cu-list menu-avatar">
                                                        <view class="cu-item" style="height: 130rpx">
                                                            <image
                                                                :src="item.logoUrl"
                                                                class="cu-avatar round lg"
                                                                style="border: 3rpx solid #f5f5f5; width: 80rpx; height: 80rpx"
                                                                :lazy-load="true"
                                                            ></image>
                                                            <!-- <view class="cu-avatar round lg" style="background-image:url({{item.Cover}});border: 3rpx solid #f5f5f5;width: 80rpx;height: 80rpx;"></view> -->
                                                            <view class="content flex-sub" style="left: 130rpx">
                                                                <view style="font-size: 24rpx; height: 40rpx">{{ item.association[0].detail }}</view>
                                                                <view class="text-gray text-sm flex justify-between" style="font-size: 24rpx; height: 40rpx">
                                                                    截止时间:{{ item.endTime }}
                                                                </view>
                                                            </view>
                                                        </view>
                                                    </view>
                                                    <view class="text-content">
                                                        {{ item.Title }}
                                                    </view>
                                                    <view class="grid flex-sub padding-lr col-1">
                                                        <image class="bg-img only-img" :src="item.Cover" mode="widthFix"></image>
                                                    </view>
                                                    <view
                                                        class="text-gray text-sm text-right padding"
                                                        style="padding: 30rpx; display: flex; align-items: center; justify-content: center"
                                                        @tap="goBao"
                                                        :data-index="index"
                                                    >
                                                        <view
                                                            class="ttt"
                                                            style="
                                                                background-color: #7cba7c;
                                                                color: white;
                                                                border-radius: 20rpx;
                                                                display: flex;
                                                                align-items: center;
                                                                justify-content: center;
                                                                width: 375rpx;
                                                                height: 50rpx;
                                                            "
                                                        >
                                                            <view style="margin-right: 10rpx">报</view>
                                                            <view>名</view>
                                                        </view>
                                                    </view>
                                                </view>
                                            </view>
                                        </view>
                                    </view>
                                </scroll-view>
                            </view>
                        </scroll-view>
                    </view>
                </view>
            </swiper-item>

            <swiper-item class="swiperitem">
                <view class="style-roll">
                    <view class="showgroup_card">
                        <scroll-view class="scrollView" :scroll-y="true">
                            <!-- 社团标题 -->
                            <!-- <view class="cu-bar bg-white solid-bottom">
                <view class='action'>
                  <text class='cuIcon-titles text-orange '></text> <view class="action_text">We校园社团</view>
                </view>
              </view> -->
                            <!-- 社团活动卡片 -->
                            <view
                                class="cu-card dynamic"
                                style="filter: invert(1) !important"
                                @tap="gotoSchedule"
                                :data-content="item"
                                v-for="(item, index) in my_act"
                                :key="index"
                            >
                                <view class="cu-item shadow" style="margin: 30rpx">
                                    <view class="cu-list menu-avatar">
                                        <view class="cu-item">
                                            <image :src="item.content.logoUrl" class="cu-avatar round lg"></image>
                                            <!-- <view class="cu-avatar round lg" style="background-image:url({{item.content}});"></view> -->
                                            <view class="content flex-sub">
                                                <view>{{ item.content.Title }}</view>
                                                <view class="text-gray text-sm flex justify-between">截止时间:{{ item.content.endTime }}</view>
                                            </view>
                                            <view class="ttt" :style="'margin-right: 25rpx;font-size: 28rpx;color: ' + (item.status == '通过' ? '#98c898' : '#cc463d') + ';'">
                                                {{ item.status }}
                                            </view>
                                        </view>
                                    </view>
                                    <view class="text-content">
                                        {{ item.content.Title }}
                                    </view>
                                    <view class="grid flex-sub padding-lr col-1">
                                        <image :src="item.content.Cover" class="bg-img only-img" mode="widthFix"></image>

                                    </view>
                                    <view class="text-gray text-sm text-right padding"></view>
                                </view>
                            </view>
                        </scroll-view>
                    </view>
                </view>
            </swiper-item>
        </swiper>
    </view>
</template>

<script>
// pages/association/activity_schedule/activity_schedule.js pages/association_new/club_activity/club_activity
export default {
    data() {
        return {
            navState: 0,

            //导航状态
            VerticalNavTop: 0,

            TabCur: 0,

            list:'',
            MainCur: 0,
            load: true,

            listCur: {
                id: '0',
                name: 'we校园'
            },

            myActivity:'',
            mess_list: '',
            my_act: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad(options) {
		// 本地测试数据
        this.myAct();
        const args = uni.getStorageSync('args');
        const school = args.school;
		let mess_list=[]
		let my_act=[]
 		let testdata = {
			logoUrl:"../../../../static/logo.png",
			Cover:"../../../../static/logo.png",
			endTime:"2022.09.22",
			Title:"test Title",
			association:[
				{
					detail:"test detail",
				}
			],
			
		}
		let testdata2 = {
			content:{
				logoUrl:"../../../../static/logo.png",
				Title:"TestTitle",
				endTime:"2020.09.22",
				Cover:"../../../../static/logo.png",
			},
			status:"通过"
		}
		for(let i=1;i<=3;i++){
			mess_list.push(testdata)
			my_act.push(testdata2)
		} 
		this.setData({
		    mess_list: mess_list,
			my_act:my_act
		});
		// 接口调用
		// 添加真实接口

    },
    methods: {
        gotoSchedule(e) {
            let content_n = e.currentTarget.dataset.content;
            console.log(content_n);
            let content = JSON.stringify(content_n);
            uni.navigateTo({
                url: '../activity_schedule/index?content=' + content
            });
        },

        tabSelect(e) {
            this.setData({
                TabCur: e.currentTarget.dataset.id,
                MainCur: e.currentTarget.dataset.id,
                VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50
            });
        },

        VerticalMain(e) {
            let that = this;
            let list = this.list;
            let tabHeight = 0;

            if (this.load) {
                for (let i = 0; i < list.length; i++) {
                    let view = uni.createSelectorQuery().select('#main-' + list[i].id);
                    view.fields(
                        {
                            size: true
                        },
                        (data) => {
                            list[i].top = tabHeight;
                            tabHeight = tabHeight + data.height;
                            list[i].bottom = tabHeight;
                        }
                    ).exec();
                }

                that.setData({
                    load: false,
                    list: list
                });
            }

            let scrollTop = e.detail.scrollTop + 20;

            for (let i = 0; i < list.length; i++) {
                if (scrollTop > list[i].top && scrollTop < list[i].bottom) {
                    that.setData({
                        VerticalNavTop: (list[i].id - 1) * 50,
                        TabCur: list[i].id
                    });
                    return false;
                }
            }
        },

        // 顶部导航栏
        bindchange(e) {
            console.log(e.detail.current);
            let index = e.detail.current;
            this.setData({
                navState: index
            });
        },

        //点击导航
        navSwitch: function (e) {
            //console.log(e.currentTarget.dataset)
            let index = e.currentTarget.dataset.index;
            this.setData({
                navState: index
            });
        },

        myAct() {
			//接口调用
			//添加真实接口
        },

        goBao(e) {
            let index = e.currentTarget.dataset.index;
            let content = JSON.stringify(this.mess_list[index]);
			//不进行页面跳转，添加一个接口，点击报名后将活动信息写入我的活动
            // uni.navigateTo({
            //     url: '../Freshman/index?content=' + content
            // });
        }
    }
};
</script>
<style>
@import './index.css';
</style>

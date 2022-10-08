<template>
    <view class="all">
        <view class="container">
            <view class="title">
                <view class="title_bg"></view>

                <view class="title_text">
                    <!-- <text>社团</text> -->
                </view>
            </view>

            <!-- <scroll-view scroll-y class="scrollPage" style="margin-top: 500rpx;"> -->
            <view class="bigcard" style="margin-top: 350rpx">


                <view class="nav-list new-bg">
                    <view
                        :data-url="item.url"
                        :class="'nav-li bg-' + item.color + ' animation-scale-up'"
                        @tap="toggle"
                        :data-index="index"
                        :data-title="item.title"
                        v-for="(item, index) in elements"
                        :key="index"
                    >
                        <view class="nav-title">{{ item.title }}</view>

                        <view class="nav-name">{{ item.name }}</view>
                    </view>
                </view>
            </view>
            <!-- </scroll-view> -->

            <!-- <view class="help">
    <text bindtap="showHelp">帮助</text>
    <icon type="info_circle" size="30rpx" bindtap="showHelp"></icon>
  </view> -->

       
        </view>
    </view>
</template>

<script>
// pages/association/association.js
export default {
    data() {
        return {
            index: '99',

            elements: [
                {
                    title: '社团招新',
                    name: 'Club activities',
                    color: 'cyan',
                    index: '1',
                    url: '/pages/index/association/club_activity/index'
                },
                {
                    title: '社团列表',
                    name: 'Community list',
                    color: 'cyan',
                    index: '2',
                    url: '/pages/index/association/club_list/index'
                },
                {
                    title: '我参加的活动',
                    name: 'Activity schedule',
                    color: 'cyan',
                    index: '3',
                    url: '/pages/index/association/my_activity/index'
                },
                {
                    title: '社团机构管理',
                    name: 'management',
                    color: 'cyan',
                    index: '4',
                    url: '/pages/index/association_manage/index',
                    ani: 'scale-up'
                },
                {
                    title: '敬请期待...',
                    name: 'Stay tuned for',
                    color: 'cyan',
                    index: '4',
                    url: '',
                    ani: 'scale-up'
                }
            ],

            pages: [
                {
                    name: '我的社团',
                    icon: '/static/pages/association_new/img/change.png',
                    path: './listDisplay/listDisplay'
                },
                {
                    name: '社团活动',
                    icon: '/static/pages/association_new/img/dan_change.png',
                    path: './listDisplay/listDisplay'
                },
                {
                    name: '社团列表',
                    icon: '/static/pages/association_new/img/delete.png',
                    path: './listDisplay/listDisplay'
                },
                {
                    name: '活动进度',
                    icon: '/static/pages/association_new/img/duo_change.png',
                    path: './listDisplay/listDisplay'
                }
            ],

            animation: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad(options) {
		//测试
		// uni.request({
		// 	url:"http://127.0.0.1:4523/m1/1710071-0-default/my_activity/list",
		// 	method:"POST",
		// 	data:{
				
		// 	},
		// 	success(res){
		// 		console.log(res)
		// 	},fail(e){
		// 		console.log(e)
		// 	}
		// })
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {},
    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {},
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {},
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {},
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {},
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {},
    methods: {
        // 跳转
        route(e) {
            console.log(this.pages[e.currentTarget.id]);
            let data = this.pages[e.currentTarget.id];
            uni.navigateTo({
                url: data.path + '?from=' + data.name
            });
        },

        toggle(e) {
            let index = e.currentTarget.dataset.index;
            let title = e.currentTarget.dataset.title;
            console.log(index);
            var anmiaton = 'scale-up';
            var that = this;
            that.setData({
                animation: anmiaton,
                index
            });
            setTimeout(function () {
                that.setData({
                    animation: ''
                });
            }, 1000); //做判断没有arg不进社团机构管理

            let url = e.currentTarget.dataset.url;
			console.log(url)
        
 
                uni.reLaunch ({
                    url: url
                });

            if (title == '敬请期待...') {
                console.log(title);
                uni.showToast({
                    title: '敬请期待...',
                    icon: 'none'
                });
            }
        }
    }
};
</script>
<style>
@import './index.css';
</style>

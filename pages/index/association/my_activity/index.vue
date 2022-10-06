<!-- 参与的活动 -->
<template>
    <view>
        <!-- <view class="cu-bar bg-white solid-bottom">
                <view class='action'>
                  <text class='cuIcon-titles text-orange '></text> <view class="action_text">We校园社团</view>
                </view>
              </view> -->
        <!-- 社团活动卡片 -->
        <view class="cu-card dynamic" v-if="act_list" @tap="gotoSchedule" :data-content="item" v-for="(item, index) in act_list" :key="index">
            <view class="cu-item shadow" style="margin: 30rpx">
                <view class="cu-list menu-avatar">
                    <view class="cu-item">
                        <image :src="item.content.logoUrl" class="cu-avatar round lg ttt"></image>
                        <!-- <view class="cu-avatar round lg ttt" style="background-image:url({{item.url}});"></view> -->
                        <view class="content flex-sub">
                            <view>{{ item.content.association[0].detail }}</view>
                            <view class="text-gray text-sm flex justify-between">
                                {{ item.time }}
                            </view>
                        </view>
                        <view class="ttt" :style="'margin-right: 25rpx;font-size: 28rpx;color: ' + (item.status == '通过' ? '#98c898' : '#cc463d') + ';'">{{ item.status }}</view>
                    </view>
                </view>
                <view class="text-content">
                    {{ item.content.Title }}
                </view>
                <view class="grid flex-sub padding-lr col-1">
                    <image :src="item.content.Cover" class="bg-img only-img ttt" mode="aspectFill"></image>
                    <!-- <view class="bg-img only-img ttt" style="background-image:url({{item.image}});" wx:key="{{index}}">
                    </view> -->
                </view>
                <view class="text-gray text-sm text-right padding"></view>
            </view>
        </view>
        <view v-if="act_list" style="width: 100vw; display: flex; align-items: center; justify-content: center; margin-top: 50rpx; padding-bottom: 100rpx">-无更多内容-</view>
        <view v-if="!act_list" style="width: 100vw; display: flex; align-items: center; justify-content: center; margin-top: 100rpx">-暂时没有数据，快去报名吧-</view>
    </view>
</template>

<script>
// pages/association/my_activity/my_activity.js
export default {
    data() {
        return {
            myActivity: [
                {
                    url: 'https://636c-cloud1-6gtqj1v4873bad50-1307814679.tcb.qcloud.la/WeSchool.jpg?sign=96086122a97f7440a80a9518373319b3&t=1661712461',
                    title: 'we校园活动一',
                    text: '2022招新啦!!!点击卡片报名吧',
                    image: 'https://636c-cloud1-6gtqj1v4873bad50-1307814679.tcb.qcloud.la/WeSchool.jpg?sign=96086122a97f7440a80a9518373319b3&t=1661712461',
                    sendTime: '2022年5月20日',
                    status: '进行中'
                },
                {
                    url: 'https://636c-cloud1-6gtqj1v4873bad50-1307814679.tcb.qcloud.la/WeSchool.jpg?sign=96086122a97f7440a80a9518373319b3&t=1661712461',
                    title: 'we校园活动二',
                    text: '2023招新啦!!!点击卡片报名吧',
                    image: 'https://636c-cloud1-6gtqj1v4873bad50-1307814679.tcb.qcloud.la/WeSchool.jpg?sign=96086122a97f7440a80a9518373319b3&t=1661712461',
                    sendTime: '2022年5月20日',
                    status: '已结束'
                }
            ],

            act_list: ''
        };
    },
    onLoad(options) {
        uni.showLoading({
            title: '加载中'
        });
        wx.cloud
            .callFunction({
                name: 'association',
                data: {
                    action: 'myApply',
                    data: {}
                }
            })
            .then((res) => {
                console.log(res);
                uni.hideLoading({
                    success: (res) => {}
                });
                this.setData({
                    act_list: res.result.data.data
                });
            });
    },
    methods: {
        gotoSchedule(e) {
            let content_n = e.currentTarget.dataset.content;
            console.log(content_n);
            let content = JSON.stringify(content_n);
            uni.navigateTo({
                url: '../activity_schedule/activity_schedule?content=' + content
            });
        }
    }
};
</script>
<style>
@import './index.css';
</style>

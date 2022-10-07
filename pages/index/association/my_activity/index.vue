<template>
	<view>
    <!-- <view class="cu-bar bg-white solid-bottom">
                    <view class='action'>
                      <text class='cuIcon-titles text-orange '></text> <view class="action_text">We校园社团</view>
                    </view>
                  </view> -->
            <!-- 社团活动卡片 -->
			
            <view class="cu-card dynamic" v-if="act_list" @tap="gotoSchedule" :data-content="item" v-for="(item, index) in (act_list)" :key="index">
                <view class="cu-item shadow" style="margin: 30rpx">
                    <view class="cu-list menu-avatar">
                        <view class="cu-item">
                            <image src="https://img-blog.csdnimg.cn/20210512171051865.png" class="cu-avatar round lg ttt"></image>
							 <!-- ↑改：请求社团头像<image src="item.content.logoUrl" class="cu-avatar round lg ttt"></image> -->
                            <!-- <view class="cu-avatar round lg ttt" style="background-image:url({{item.url}});"></view> -->
                            <view class="content flex-sub">
                                <view>{{ item.content.association[0].detail }}</view>
                                <view class="text-gray text-sm flex justify-between">
                                    {{ item.content.time }}
                                </view>
                            </view>
                            <view class="ttt" :style="'margin-right: 25rpx; font-size: 28rpx; font-weight: 550;  color: ' + (item.content.status == '通过' ? '#98c898' : '#cc463d') + ';'">{{ item.content.status }}</view>
                        </view>
                    </view>
                    <view class="text-content">
                        {{ item.content.Title }}
                    </view>
                    <view class="grid flex-sub padding-lr col-1">
						<image src="https://img-blog.csdnimg.cn/20210512171051865.png" class="bg-img only-img ttt" mode="aspectFill"></image>
                        <!-- ↑改：请求cover图片 <image src="item.content.Cover" class="bg-img only-img ttt" mode="aspectFill"></image> -->
                        <!-- <view class="bg-img only-img ttt" style="background-image:url({{item.image}});" wx:key="{{index}}">
                        </view> -->
                    </view>
                    <view class="text-gray text-sm text-right padding"></view>
                </view>
            </view>
            <view v-if="act_list" style="font-size: 26rpx; color: #ababab; width: 100vw; display: flex; align-items: center; justify-content: center; margin-top: 50rpx; padding-bottom: 100rpx; " >- - 无更多内容 - -</view>
            <view v-if="!act_list" style="color: #ababab; width: 100vw; display: flex; align-items: center; justify-content: center; margin-top: 100rpx">- - 暂时没有数据，快去报名吧 - -</view>

	</view>
</template>

<script>
	// pages/association_new/activity_schedule/activity_schedule.js
		export default {
			data() {
				return {	
					act_list:[
						{content:{
							Title:"纳新活动",
                            time:"2022.10.10",
							association: [
							 {name:"swimcl",detail:"游泳社"},],
							 
							 Cover:"",
							 logoUrl: "Image(images/hyjj.jpg)",
							 status:"未通过",
							   }},
						{content:{
							   	Title:"纳新活动",
							       time:"2022.10.5",
							   	association: [
							   	 {name:"runcl",detail:"跑步社"},],
							   	 
							   	 Cover:"cover喽",
							   	 
							   	 status:"通过",
							   	   }},
							]
							
				}
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
			  methods: {}
				}
		
</script>

<style lang="scss">
	@import "./my_activity.css";
</style>

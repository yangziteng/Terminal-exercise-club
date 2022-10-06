<!-- 参与的活动-> 活动进度 -->
<template>
	<view>
		<view class="cu-timeline">
			<view class="cu-time" style="width: auto;">感谢您报名我们的活动,请及时查看活动信息</view>
			<view class="cu-item">
				<view class="content" style="display: flex;flex-direction: column;">
					<view><text>活动：</text>{{content.content.Title}}</view>
					<view v-for="(item,index) in (content.content.association)" :key="index"><text>{{item.name}}：</text>

						{{item.detail}}
					</view>
				</view>
			</view>
		</view>

		<view class="cu-timeline">
			<view class="cu-time" style="width: 350rpx;">{{content.time}}</view>
			<view class="cu-item">
				<view class="content" style="display: flex;flex-direction: column;">
					<view><text>成功报名</text>{{content.content.association[0].detail}}的： {{content.content.Title}}</view>
					<view><text>姓名：</text> {{content.formData['姓名']}}</view>
					<view><text>学号：</text> {{content.formData['学号']}}</view>
					<view><text>联系方式：</text> {{content.formData['联系方式']}}</view>
					<view style="display: flex;"><text>选择的部门：</text>
						<view v-for="(item,index) in (content.formData[" 选择的部门"])" :key="index"><text :decode="true">{{item}}&ensp;</text>

						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="cu-timeline" v-for="(item,index) in (content.interviewMsg)" :key="index">



			<view class="cu-time">{{item.sendTime}}</view>





			<view :class="'cu-item '+((item.index+1) == content.interviewMsg.length?'text-blue':'')">
				<view class="content">
					<view class="cu-capsule radius">
						<view class="cu-tag bg-cyan">{{item.time}}</view>
						<!-- <view class="cu-tag bg-cyan">上午</view> -->
						<!-- <view class="cu-tag line-cyan" style="margin-left: 0;">10:00</view> -->
					</view>
					<view class="margin-top">标题: {{item.title}}</view>
					<view class="margin-top">地点: {{item.site}}</view>
					<view class="margin-top">材料: {{item.need}}</view>
					<view class="margin-top">联系方式: {{item.phonenum}}</view>
					<view class="margin-top">备注: {{item.remark}}</view>
				</view>
			</view>




		</view>
	</view>
</template>

<script>
	// pages/association_new/activity_schedule/activity_schedule.js
	export default {
		data() {
			return {
				content: {
					content: {
						association: []
					},

					time: "",
					interviewMsg: []
				}
			};
		},
		/**
			* 生命周期函数--监听页面加载
			*/
		onLoad(options) {
			let content = JSON.parse(options.content);
			console.log(content);

			if (content.interviewMsg) {
				for (let i = 0; i < content.interviewMsg.length; i++) {
					content.interviewMsg[i].index = i;
				}
			}

			this.setData({
				content
			});
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
	};
</script>
<style>
	@import "./index.css";
</style>
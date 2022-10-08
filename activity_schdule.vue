<template>
	<view>
			
		<view class="cu-timeline">
				<view class="cu-time" style="width: auto; margin-top: 20rpx;">感谢您报名我们的活动,请及时查看活动信息</view>
				<view class="cu-item">
					<view class="content" style="display: flex;flex-direction: column;">
						<view><text >活动：</text>{{content.content.Title}}</view>
						<view v-for="(item,index) in (content.content.association)" :key="index"><text style="line-height: 60rpx;" >{{item.name}}：</text>
		
							{{item.detail}}
						</view>
					</view>
				</view>
			</view>
			

		
		
		<view class="cu-timeline">
			  <view class="cu-time" style="width: 120rpx;">{{content.time}}</view>
				<view class="cu-item">
					<view class="content" style="display: flex;flex-direction: column;">
						<view><text style="line-height: 60rpx;">成功报名</text> <text style="font-weight:550;">{{content.content.association[0].detail}} </text> <text >的：</text> {{content.content.Title}}</view>
						<view><text style="line-height: 60rpx; ">姓名：</text> {{content.formData['姓名']}}</view>
						<view><text style="line-height: 60rpx; ">学号：</text> {{content.formData['学号']}}</view>
						<view><text style="line-height: 60rpx; ">联系方式：</text> {{content.formData['联系方式']}}</view>
						<view style="display: flex;"><text style="line-height: 60rpx; ">选择的部门：</text>
							<view v-for="(item,index) in (content.formData['选择的部门'])" :key="index"><text :decode="true" style="line-height: 60rpx; ">{{item}}&ensp;</text>

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
									<view class="cu-tag bg-cyan">活动时间：{{item.time}}</view>
			
								</view>
								<view class="margin-top">活动标题: {{item.title}}</view>
								<view class="margin-top">面试/活动地点: {{item.site}}</view>
								<view class="margin-top">所需材料: {{item.need}}</view>
								<view class="margin-top">联系方式: {{item.phonenum}}</view>
								<view class="margin-top">备注: {{item.remark}}</view>
							</view>
						</view>
				</view>
			

		
		
	</view>
</template>


<script >
// pages/association_new/activity_schedule/activity_schedule.js
		export default {
			data() {
				return {
				content:{},
				};
			},
			onLoad() {
			   uni.request({
			   	url:"http://127.0.0.1:4523/m1/1710071-0-default/my_activity/list",
			   	method:"POST",
			   	success:res=>{
			   	    console.log(res)
					this.content=res.data.result.data.data[0]
			   			   },
			   })
			},
			methods: {
				gotoSchedule() {
								uni.navigateTo({
									url:"/pages/schedule/schedule"
								})
					
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
			}
			
			

		

</script>

<style lang="scss">
  @import "./schedule.css";
</style>


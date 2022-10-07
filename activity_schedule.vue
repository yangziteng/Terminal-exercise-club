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
					content: {
						content: {
							Title:"air实验室纳新活动", 
							association: [
							 {name:"社团/机构",detail:"air实验室"},
							 {name:"联系方式",detail:"0123-3456778"},
							 {name:"联系人",detail:"小张"},
							 {name:"学号",detail:"22122212"},
							 {name:"社团详情",detail:"成立于2019年3月，培养学生在人工智能与机器人领域的编程及设计能力。"},
							 {name:"设立部门",detail:"项目组、视频组、文案组"},]
						},
						formData:{
							姓名:"小白",
							学号:"22210034",
							联系方式:"0010-7654321",
							选择的部门:"项目组、宣传部",
							},
						time: "2020-1-2",
						interviewMsg: [
							{title:"游泳社" ,sendTime:"2022.10.10", time:"10月15日 10：00",site:"操场",need:"doc",phonenum:"0000-1",remark:"请您准时到场"},
							{title:"跑步社" , sendTime:"2022.11.10", time:"10月10日 16：00",site:"操场",need:"doc",phonenum:"0000-2",remark:"请您准时到场"},
							{title:"骑行社" , sendTime:"2022.12.10", time:"11月8日 14：00",site:"操场",need:"doc",phonenum:"0000-3",remark:"请您准时到场"}]
					}
				}
			},
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
				methods: {},
			}
			

		

</script>

<style lang="scss">
  @import "./activity_schedule.css";
</style>


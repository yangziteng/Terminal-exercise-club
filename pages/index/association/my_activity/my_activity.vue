<template>
	<view>
    <!-- <view class="cu-bar bg-white solid-bottom">
                    <view class='action'>
                      <text class='cuIcon-titles text-orange '></text> <view class="action_text">We校园社团</view>
                    </view>
                  </view> -->
            <!-- 社团活动卡片 -->
            <view class="cu-card dynamic" v-if="act_list"  :data="item" v-for="(item,id) in (act_list)" :key="id" @click="gotoSchedule(item.id)">
                <view class="cu-item shadow" style="margin: 30rpx">
                    <view class="cu-list menu-avatar">
                        <view class="cu-item">
                            <image :src="item.logo" class="cu-avatar round lg ttt"></image>
							 
                            <!-- <view class="cu-avatar round lg ttt" style="background-image:url({{item.url}});"></view> -->
                            <view class="content flex-sub">
                                <view>{{ item.academic }}</view>
                                <view class="text-gray text-sm flex justify-between">
                                    {{ item.updateTime }}
                                </view>
                            </view>
                            <view class="ttt" :style="'margin-right: 25rpx; font-size: 28rpx; font-weight: 550;  color: ' + (item.status == '通过' ? '#98c898' : '#cc463d') + ';'">{{ item.status }}</view>
                        </view>
                    </view>
                    <view class="text-content">
                        {{ item.name }}
                    </view>
                    <view class="grid flex-sub padding-lr col-1">
						<image :src="item.logo" class="bg-img only-img ttt" mode="aspectFill"></image>
                        
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

		export default {
			  data() {
				  
			  	return {
			  	act_list:[]
				
			  	};
			  },

			  methods: {
			  	gotoSchedule(e) {
			       console.log(e);
			  		uni.navigateTo({
			  			url:"/association/activity_schedule/schedule?id="+e
			  					})
			  	        },
				
				getData(){
					uni.request({
						
						url:"https://mock.apifox.cn/m2/1457454-0-default/42870178",
						// method:"POST",
						success:res=>{
						    console.log(res)
								  		this.act_list=res.data.data
								   },
				})
				}
				
			  },
			  
			  onLoad() {
			     this.getData();
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
	@import "./my_activity.css";

</style>

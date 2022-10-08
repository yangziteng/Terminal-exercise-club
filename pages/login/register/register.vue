<template>
	<view class="sun-index">
		<view class="sun-logo-box">
			<view class="sun-logo">
				<image class="sun-icon-img" src="@/static/imgs/moom_white.png" />
			</view>
		</view>
		<view class="sun-login-box">
			<view class="sun-label">
				<image style="width: 28rpx;height:39rpx;" src="@/static/imgs/mobile_icon.png" />
				<text class="label-text">联系方式</text>
			</view>
			<view class="sun-input-box">
				<input v-model="phone" type="text" placeholder="请输入手机号" placeholder-class="placeholder-class" />
				<image @click="clear" id="phone" class="close-icon" src="@/static/imgs/close_icon.png" />
			</view>
		</view>
		<view class="sun-login-box">
			<view class="sun-label">
				<image style="width: 28rpx;height:39rpx;" src="@/static/imgs/mobile_icon.png" />
				<text class="label-text">姓名</text>
			</view>
			<view class="sun-input-box">
				<input v-model="name" type="text" placeholder="请输入姓名" placeholder-class="placeholder-class" />
				<image @click="clear" id="name" class="close-icon" src="@/static/imgs/close_icon.png" />
			</view>
		</view>
		<view class="sun-login-box">
			<view class="sun-label">
				<image style="width: 29rpx;height:29rpx;" id="username" src="@/static/imgs/code_icon.png" />
				<text class="label-text">学号</text>
			</view>
			<view class="sun-input-box">
				<input v-model="username" type="text" placeholder="请输入学号" placeholder-class="placeholder-class" />
				<image @click="clear" id="name" class="close-icon" src="@/static/imgs/close_icon.png" />
			</view>
		</view>
		<view class="sun-login-box">
			<view class="sun-label">
				<image style="width: 29rpx;height:37rpx;" src="@/static/imgs/pwd_icon.png" />
				<text class="label-text">密码</text>
			</view>
			<view class="sun-input-box">
				<input v-model="password" type="text" placeholder="请输入密码" password="true"
					placeholder-class="placeholder-class" />
				<image @click="clear" id="password" class="close-icon" src="@/static/imgs/close_icon.png" />
			</view>
		</view>
		<view class="login-btn-box">
			<view class="login-btn" @click="handleSubmit">注册</view>
		</view>
	</view>
</template>

<script setup>
	export default {
		data() {
			return {
				username: '',
				password: '',
				name: '',
				phone: "",
				countTime: 60,
				currentCountTime: 0,
				showTime: false,
				timeId: null
			}
		},

		methods: {
			clear(e) {
				this.setData({
					[e.currentTarget.id]: ""
				})
			},
			handleSubmit() {
				if (!this.username) return uni.showToast({
					title: "请输入账号",
					icon: 'none',
					duration: 1500
				})
				if (!this.name) return uni.showToast({
					title: "请输入名字",
					icon: 'none',
					duration: 1500
				})
				if (!this.phone) return uni.showToast({
					title: "请输入手机号",
					icon: 'none',
					duration: 1500
				})
				if (!this.password) return uni.showToast({
					title: '请输入密码',
					icon: 'none',
					duration: 1500
				})
				uni.showToast({
					title: t('common.success'),
					duration: 1500
				})
				uni.request({
					url: "",
					method:"POST",
					data: {
						"userNumber": this.username,
						"password": this.password,
						"tel": this.phone,
						"name": this.name
					},
					success(res){
						console.log(res)
						if(res.data=="注册成功"){
							uni.showToast({
								title:"注册成功"
							})
							uni.navigateTo({
								url:"/pages/index/index"
							})
						}
					},
					fail(e){
						console.log(e)
					}
				})
			},
			
		}

	}
</script>

<style scoped>
	.sun-logo-box {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 750rpx;
		height: 300rpx;
	}

	.sun-icon-img {
		width: 120rpx;
		height: 120rpx;
	}

	.sun-logo {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 180rpx;
		height: 180rpx;
		border-radius: 15rpx;
		background-color: #12C8B9;
		box-shadow: 0rpx 0rpx 30rpx rgba(18, 200, 185, 0.5);
	}

	.close-icon {
		width: 36rpx;
		height: 34rpx;
	}

	.sun-login-box {
		padding: 20rpx 60rpx;
	}

	.sun-label {
		display: flex;
		align-items: center;
	}

	.label-text {
		margin-left: 16rpx;
		font-weight: 500;
		color: #272e2d;
		font-size: 30rpx;
	}

	.sun-input-box {
		display: flex;
		align-items: center;
		height: 100rpx;
		border-bottom: 1rpx solid #eee;
		padding: 0px 10rpx;
	}

	.sun-input-box input {
		flex: 1;
	}

	.placeholder-class {
		font-size: 30rpx;
		color: #C0C0C0;
	}

	.sun-tip {
		display: flex;
		justify-content: space-between;
		padding: 0rpx 68rpx;
	}

	.sun-tip-text {
		color: #30C6B3;
	}

	.login-btn-box {
		position: absolute;
		bottom: 129rpx;
		left: 0;
		width: 620rpx;
		padding: 50rpx 68rpx 0rpx;
	}

	.login-btn {
		height: 82rpx;
		border-radius: 41rpx;
		background-color: #12C8B9;
		box-shadow: -1px 12px 11px 0px rgba(16, 170, 157, 0.4);
		text-align: center;
		line-height: 82rpx;
		font-size: 36rpx;
		font-weight: 500;
		color: #fff;
	}

	.code-text {
		font-size: 28rpx;
		color: #30C6B3;
	}

	.gray {
		color: #C0C0C0;
	}
</style>

<template>
	<view class="sun-index">
		<view class="sun-logo-box">
			<view class="sun-logo">
				<image class="sun-icon-img" src="@/static/logo2.png" />
			</view>
		</view>
		<view class="sun-login-box">
			<view class="sun-label">
				<image style="width: 28rpx;height:39rpx;" src="@/static/imgs/mobile_icon.png" />
				<text class="label-text">账号</text>
			</view>
			<view class="sun-input-box">
				<input v-model="username" type="text" placeholder="请输入账号" placeholder-class="placeholder-class" />
				<image class="close-icon" src="@/static/imgs/close_icon.png" />
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
				<image class="close-icon" src="@/static/imgs/close_icon.png" />
			</view>
		</view>
		<view class="sun-tip">
			<text class="sun-tip-text">忘记密码</text>
			<text class="sun-tip-text" @click="goRegister">注册</text>
		</view>
		<view class="login-btn-box">
			<view class="login-btn" @click="handleSubmit">点击登录</view>
		</view>
	</view>
</template>

<script setup>
	export default {
		data() {
			return {
				username: "",
				password: ""
			}
		},
		methods: {
			 handleSubmit() {
				 var that = this
				console.log(this.username, this.password)
				if (!this.username) return uni.showToast({
					title: '学号为空',
					icon: 'none',
					duration: 1500
				})
				if (!this.password) return uni.showToast({
					title: '密码为空',
					icon: 'none',
					duration: 1500
				})
				var that = this
				uni.showLoading({
					title:'数据请求加载中'
				})
				//这步验证
				uni.request({
					url:'https://mock.apifox.cn/m2/1457454-0-default/42651095',
					method:"POST",
					data:{
						userNumber:this.username,
						password:this.password
					},
					success(res){
						console.log(res)
						uni.hideLoading()
						uni.setStorageSync("args",{
							username:that.username,
							name:that.name
						})
					}
				})
				//验证之后分为成功和失败
				uni.showToast({
					title: '登录成功',
					duration: 1500
				})
				setTimeout(() => {
					uni.switchTab({
						url: '/pages/index/index'
					})
				}, 1000)
			},

			 goRegister() {
				 console.log(2222)
				uni.navigateTo({
					url: './register/register'
				})
			}
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

	.sun-icon-img {
		width: 180rpx;
		height: 180rpx;
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
		width: 670rpx;
		padding: 50rpx 0rpx;
		margin: 0rpx 30rpx
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
</style>

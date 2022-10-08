<template>
	<view>
		<view class="wrap" v-if="HtmlStatus == 0">
			<view class="caption">
				<view class="title">社团招新信息</view>
			</view>
			<!-- <view class="tip">·请填写真实信息</view> -->
			<!-- <view class="tip">·个人信息不用于展示</view> -->
			<view class="list_wrap">
				<form @submit="formSubmit">
					<view class="list_item">
						<view class="left2">上传logo</view>
						<view v-if="!img" @tap="add_logo">
							<image class="add_logo_img" data-name="logo" src="./images/paizhao.png"></image>
						</view>
						<view v-if="img" @tap="add_logo">
							<image class="add_logo_img" data-name="logo" :src="imgUrl"></image>
						</view>
						<!-- <view class="add_logo" bind:tap="{{img==false?'add_logo':'showTip'}}">
          {{img==false?'+':'已上传'}}
        </view> -->
					</view>
					
					<view class="list_item" v-for="(item, index) in list" :key="index">
						<view style="display: flex; flex: 1" v-if="item.title == '社团介绍'">
							<view class="left">{{ item.title }}</view>
							<textarea :placeholder="item.placeholder" style="height: 230rpx" class="rigth"
								:name="item.id" :type="item.type" :value="item.value" maxlength="300" />
						</view>

						<view style="display: flex; flex: 1" v-else>
							<view class="left">{{ item.title }}</view>
							<input :placeholder="item.placeholder" class="rigth" :name="item.id" :type="item.type"
								:value="item.value" />
						</view>
					</view>
					<view class="list_item">
						<view class="left">级别</view>
						<picker @change="bindPickerChange" class="rigth" :value="arrayIndex" :range="array">
							<view>{{ array[arrayIndex] }}</view>
						</picker>
					</view>

					<view class="list_item" v-if="arrayIndex == 1">
						<view class="left">学院名称</view>

						<picker @change="bindPickerChange2" class="rigth" :value="arrayIndex2" :range="array2">
							<view>{{ array2[arrayIndex2] }}</view>
						</picker>
					</view>
					<view class="list_item">
						<view class="left">
							<view style="height: 40rpx">设立部门</view>
							<view style="font-size: 20rpx; padding-top: 0rpx">(空格分隔)</view>
						</view>

						<input placeholder="策划部 事务部 综合部 设计部 技术部" class="rigth" name="department" v-model="department" />
					</view>

					<button class="submit" size="mini" formType="submit">提交</button>
				</form>
			</view>
		</view>
		<view class="test_wrap" v-else-if="HtmlStatus == 1">
			<view class="test_img">
				<image src="/static/images/testing.png" />
			</view>
			<view class="test_tip">资料审核中...</view>
		</view>
		<view class="test_wrap" v-else-if="HtmlStatus == 3">
			<view class="test_img">
				<image src="/static/images/delete.png" />
			</view>
			<view class="test_tip">账号已注销，如需重新开通请联系客服~</view>
		</view>
		<view class="asso_wrap" v-else-if="HtmlStatus == 2 || HtmlStatus == 4">
			<view class="mess_item">
				<view class="mess_wrap" v-if="item.name != '社团详情'" v-for="(item, index) in assoMess" :key="index">
					<view class="title">{{ item.name }}</view>

					<view class="mess">{{ item.detail }}</view>
				</view>
			</view>
			<!-- 我的社团 -->
			<view class="bt_list">
				<view class="bt_title_wrap">
					<view class="bt_title">我的社团</view>
					<view class="bt_tip">开始发布活动吧</view>
				</view>
				<view class="bt_list_wrap">
					<view class="act_bt" @tap="tap" :id="item.tapName" v-for="(item, index) in index_list" :key="index">
						<image style="height: 75rpx; width: 75rpx" :src="item.img" />

						<view class="tip_content">{{ item.name }}</view>
					</view>
				</view>
			
			</view>
		</view>
	</view>
</template>

<script>
	// pages/association/association.js


	export default {
		data() {
			return {
				department: '',
				array: ['校级', '院级', '社团', '实验室(工作室)'],
				array2: [
					'请选择学院',
					'石油工程学院',
					'化学工程学院',
					'化学学院',
					'材料科学与工程学院',
					'环境科学与工程学院',
					'生物与食品工程学院',
					'机电工程学院',
					'自动化学院',
					'电子信息工程学院',
					'理学院',
					'经济管理学院',
					'文法学院',
					'外国语学院',
					'体育学院',
					'艺术与设计学院',
					'马克思主义学院',
					'计算机学院',
					'建筑工程学院'
				],
				arrayIndex2: 0,
				arrayIndex: 0,

				list: [
					{
						title: '社团/机构',
						placeholder: '输入名称',
						type: 'text',
						value: '',
						id: 'association'
					},
					
					{
						title: '社团介绍',
						placeholder: '输入社团介绍',
						type: 'text',
						value: '',
						id: 'detail'
					} // {
					//   title: "设立部门",
					//   placeholder: "输入设立部门",
					//   type: "text",
					//   value: "",
					//   id: "department"
					// },
				],
				department:[],
				index_list: [{
						img: '/static/imgs/association/freshman.png',
						name: '招新报名',
						tapName: 'freshman'
					},
					{
						img: '/static/imgs/association/edit.png',
						name: '编辑资料',
						tapName: 'edit'
					},
					{
						img: '/static/imgs/association/freshData.png',
						name: '招新数据',
						tap: 'goFreshmanData'
					},
					{
						img: '/static/imgs/association/delete.png',
						name: '注销身份',
						tapName: 'delete'
					}
				],
				id:2,//先写死为社团id
				data_list: [{
						img: '/static/pages/association/img/freshman_data.png',
						name: '招新数据',
						tap: 'goFreshmanData'
					},
					{
						img: '/static/pages/association/img/match_data.png',
						name: '赛事反馈',
						tap: 'goMatchData'
					},
					{
						img: '/static/pages/association/img/more.png',
						name: '人员管理',
						tap: 'admin'
					},
					{
						img: '/static/pages/association/img/more.png',
						name: '创作中心',
						tap: 'loading'
					}
				],

				HtmlStatus: 0,

				//0为申请 1审核中 2审核通过  3注销中 4管理员 5不存在
				assoMess: '',

				photoStatus: false,
				img: false,
				img2: false,
				imgUrl2: '',
				imgUrl: '',
				adminCard: '',
				erweimaUrl: ''
			};
		},
		onLoad: function(options) {
			if(this.HtmlStatus==2){
				let assoMess = [{
						name: '社团/机构',
						detail: "练习生小分队"
					},
					{
						name: '联系方式',
						detail: "1581"
			  },
			 	{
						name: '联系人',
						detail: "tets"
			  },
					{
						name: '学号',
						detail: "test"
					},
					{
						name: '社团详情',
						detail: "test"
					},
					{
						name: '设立部门',
						detail: "test"
					}
				];
				this.setData({assoMess})
			}
			this.onLoadClone3389(options);
		},
		onShow() {
			this.onLoadClone3389({});
		},
		methods: {
		
			tap(e){
				console.log(e.currentTarget.id)
				if(e.currentTarget.id=="freshman"){
						this.freshman()
				}
				if(e.currentTarget.id=="edit"){
					this.edit()
				}
				if(e.currentTarget.id=="goFreshmanData"){
					this.goFreshmanData()
				}
				else{
					this.deleteFun()
				}
			},
			onLoadClone3389: function(options) {
				//判断登录了没 没有登录的话就提交表单 登录的话就显示第二个状态

				// let res = uni.getStorageSync('args');
				let list = this.list;
				// card = String(res.username);
				// school = res.school;
				// list[3].value = card;
				this.setData({
					list: list
				});
				this.search();
			},

			bindPickerChange(e) {
				this.setData({
					arrayIndex: e.detail.value
				});
			},

			bindPickerChange2(e) {
				this.setData({
					arrayIndex2: e.detail.value
				});
			},

		
			// 查询用户状态
			search() {
				
				console.log("search")
				let assoMess = [{
						name: '社团/机构',
						detail: "we校园"
					},
					{
						name: '联系方式',
						detail: "1581"
			  },
			 	{
						name: '联系人',
						detail: "tets"
			  },
					{
						name: '负责人学号',
						detail: "test"
					},
					{
						name: '社团详情',
						detail: "test"
					},
					{
						name: '设立部门',
						detail: department
					}
				];
				let erweimaUrl = res.data[0].erweimaUrl;
				this.setData({
					HtmlStatus: 4,
					adminCard: adminCard,
					assoMess,
				erweimaUrl
				});
			},
			//       search() {
			// }
			// card = String(card);
			// uni.showLoading({
			//     title: '加载中'
			// });
			// console.log(card);
			// db.collection('associationApply')
			//         .where({
			//             count: card,
			//             school: uni.getStorageSync('args').school
			//         })
			//         .get()
			//         .then((res) => {
			//             console.log(res);

			//             if (res.data.length == 0) {
			//                 db.collection('associationApply')
			//                     .where({
			//                         adminArr: _.all([card])
			//                     })
			//                     .get()
			//                     .then((res) => {
			//                         console.log(res);

			//                         if (res.data.length == 0) {
			//                             this.setData({
			//                                 HtmlStatus: 0
			//                             });
			//                         } else {
			//                             let adminCard = res.data[0].count;
			//                             let department = res.data[0].hostMess.department;
			//                             console.log(department); //  department = department.join(" ")

			//                             let assoMess = [
			//                                 {
			//                                     name: '社团/机构',
			//                                     detail: res.data[0].hostMess.association
			//                                 },
			//                                 {
			//                                     name: '联系方式',
			//                                     detail: res.data[0].hostMess.phone
			//                                 },
			//                                 {
			//                                     name: '联系人',
			//                                     detail: res.data[0].hostMess.name
			//                                 },
			//                                 {
			//                                     name: '负责人学号',
			//                                     detail: res.data[0].hostMess.card
			//                                 },
			//                                 {
			//                                     name: '社团详情',
			//                                     detail: res.data[0].hostMess.detail
			//                                 },
			//                                 {
			//                                     name: '设立部门',
			//                                     detail: department
			//                                 }
			//                             ];
			//                             let erweimaUrl = res.data[0].erweimaUrl;
			//                             this.setData({
			//                                 HtmlStatus: 4,
			//                                 adminCard: adminCard,
			//                                 assoMess,
			//                                 erweimaUrl
			//                             });
			//                         }
			//                     });
			//             } else {
			//                 if (res.data[0].status === false) {
			//                     this.setData({
			//                         HtmlStatus: 1
			//                     });
			//                 } else if (res.data[0].status == 0) {
			//                     this.setData({
			//                         HtmlStatus: 3
			//                     });
			//                 } else {
			//                     console.log(res.data[0]);
			//                     console.log(res.data[0].hostMess); // 社团操作，发布

			//                     let department = res.data[0].hostMess.department;
			//                     console.log(department); //  department = department.join(" ")

			//                     let assoMess = [
			//                         {
			//                             name: '社团/机构',
			//                             detail: res.data[0].hostMess.association
			//                         },
			//                         {
			//                             name: '联系方式',
			//                             detail: res.data[0].hostMess.phone
			//                         },
			//                         {
			//                             name: '联系人',
			//                             detail: res.data[0].hostMess.name
			//                         },
			//                         {
			//                             name: '学号',
			//                             detail: res.data[0].hostMess.card
			//                         },
			//                         {
			//                             name: '社团详情',
			//                             detail: res.data[0].hostMess.detail
			//                         },
			//                         {
			//                             name: '设立部门',
			//                             detail: department
			//                         }
			//                     ];
			//                     let erweimaUrl = res.data[0].erweimaUrl;
			//                     let adminCard = res.data[0].count;
			//                     this.setData({
			//                         HtmlStatus: 2,
			//                         assoMess: assoMess,
			//                         erweimaUrl,
			//                         adminCard
			//                     });
			//                 }
			//             }

			//             uni.hideLoading({
			//                 success: (res) => {}
			//             });
			//         });
			// },

			// 招新数据
			goFreshmanData() {
				let count = this.adminCard;
				uni.navigateTo({
					url: `../association_manage/freshmanData?count=${count}`
				});
			},

			

			formSubmit(e) {
				console.log(e);
				var department;
				let data = e.detail.value;
				console.log(data)
				if (data.association == '' || data.card == '' || data.name == '' || data.phone == '' || data.department ==
					'') {
					uni.showToast({
						title: '您还有信息还未填写~',
						icon: 'none'
					});
					return -1;
				}

				if (this.array2[this.arrayIndex2] == '请选择学院' && this.array[this.arrayIndex2] == '院级') {
					uni.showToast({
						title: '请选择学院~',
						icon: 'none'
					});
					return -1;
				} else {
					var xueyuan = '';

					if (this.array2[this.arrayIndex2] != '请选择学院' && this.array[this.arrayIndex2] == '院级') {
						//说明有学院名称要传入
						xueyuan = this.array2[this.arrayIndex2];
					}

					try {
						department = data.department.trim().split(' ');
						console.log(department);
					} catch {
						uni.showToast({
							title: '你的设立部门输入格式出错,请重新输入',
							icon: 'none'
						});
						return -1;
					}
					let args = uni.getStorageSync("args")
					uni.showModal({
						title: '提示',
						content: '确认提交',
						showCancel: true,
						cancelText: '取消',
						cancelColor: '#000000',
						confirmText: '确定',
						confirmColor: '#3CC51F',
						success: (result) => {
							if (result.confirm) {
								uni.showLoading({
									title: '提交中',
									mask: true,
									success: (result) => {
										console.log(434)
										uni.request({
												url: "https://mock.apifox.cn/m2/1457454-0-default/42693343",
												method: "POST",
												data: {
													name:args?args.name:"未登录用户",
													academic: xueyuan,
													logo: this.imgUrl,
													status: 1,
													requirement:data.association,
													type:this.array[this.arrayIndex],
													belong: department,
													desc:data.detail	
													
													
												}
											})

											.then((res) => {
												console.log(res)
												this.setData({
													HtmlStatus: 1
												});
												uni.hideLoading();
											})
											.catch(e => {
												uni.hideLoading()
												console.log(e)
											})

									}
								});
							}
						}
					});
				}
			},

		

			// 注销社团
			deleteFun() {
				let args = uni.getStorageSync('args');
				uni.showLoading({
					title: '查询中',
					mask: true,
					success: (result) => {
						uni.request({
							url:'https://mock.apifox.cn/m2/1457454-0-default/42694557',
							method:'POST',
							id:this.id,
							status:0
						})
					}
				});
			},

			// 编辑资料
			edit() {
				let args = uni.getStorageSync('args');
				uni.showLoading({
					title: '查询中',
					mask: true,
					success: (result) => {
						uni.navigateTo({
							url: '../association_manage/edit/edit?count=' + card
						});
					}
				});
			},

			// 跳转
			freshman() {
				let assoMess = this.assoMess;
				assoMess = JSON.stringify(assoMess);
				let erweimaUrl = encodeURIComponent(this.erweimaUrl);
				let count = this.adminCard;
				uni.navigateTo({
					url: '/pages/association/freshman/freshman?assoMess=' + assoMess + '&erweimaUrl=' +
						erweimaUrl + '&count=' + count
				});
			},

			// 上传logo
			add_logo({
				target: {
					dataset: {
						name
					}
				}
			}) {
				console.log(name);
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						let tempUel = res.tempFilePaths[0];
						wx.cloud
							.uploadFile({
								cloudPath: 'association/' + Date.now() + 'jpg',
								filePath: tempUel
							})
							.then((res) => {
								let imgUrl = res.fileID;
								uni.showToast({
									title: '上传成功',
									icon: 'none',
									image: '',
									duration: 1500,
									mask: false,
									success: (res) => {
										if (name == 'logo') {
											this.setData({
												img: true,
												imgUrl: imgUrl
											});
										}

										if (name == 'qun') {
											this.setData({
												img2: true,
												imgUrl2: imgUrl
											});
										}
									}
								});
							});
					}
				});
			},

			showTip() {
				uni.showToast({
					title: '审核后可修改',
					icon: 'none',
					image: '',
					duration: 1500,
					mask: false,
					success: (result) => {}
				});
			},

			// 更新人数活动数量


			admin() {
				uni.showLoading({
					title: '查询中',
					mask: true,
					success: (result) => {
						db.collection('associationApply')
							.where({
								count: card
							})
							.get()
							.then((res) => {
								if (res.data.length == 0) {
									uni.hideLoading();
									uni.showToast({
										title: '没有权限',
										icon: 'none',
										image: '',
										duration: 1500,
										mask: false,
										success: (result) => {}
									});
								} else {
									uni.hideLoading();
									uni.navigateTo({
										url: '/pages/association/admin/admin'
									});
								}
							});
					}
				});
			}
		}
	};
</script>
<style>
	@import './index.css';
</style>

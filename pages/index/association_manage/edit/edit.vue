<template>
    <view class="wrap">
        <view class="title">编辑资料</view>
        <form @submit="formSubmit">
            <view class="list_wrap">
                <view class="list" v-for="(item, index) in listObj" :key="index">
                    <view class="caption">{{ item.title }}</view>

                    <input v-if="item.name != 'detail'" :value="item.value" class="handle" @tap="tap" :id="item.tap" :name="item.name" :disabled="item.name == 'card' ? true : false" />

                    <textarea v-if="item.name == 'detail'" :value="item.value" class="handle2" @tap="tap" :id="item.tap" :name="item.name" />
                </view>
                <view class="list">
                    <view class="caption" style="flex: 0.5">群二维码</view>
                    <image @tap="change" :src="erweimaUrl" mode="aspectFill" style="width: 200rpx; height: 200rpx" v-if="erweimaUrl"></image>
                    <image @tap="change" src="/static/pages/association/img/paizhao.png" mode="aspectFill" style="width: 200rpx; height: 200rpx" v-if="!erweimaUrl"></image>
                </view>
                <view style="position: relative; bottom: 60rpx; font-size: 20rpx; color: gray"></view>
                <view class="list">
                    <view class="caption" style="flex: 0.5">社团logo</view>

                    <image :src="logoUrl" mode="aspectFill" style="width: 200rpx; height: 200rpx"></image>
                </view>
                <view style="position: relative; bottom: 60rpx; font-size: 20rpx; color: gray">(暂时不支持修改logo)</view>
            </view>
            <view class="bt_wrap">
                <button class="certain" form-type="submit">保存修改</button>
            </view>
        </form>
    </view>
</template>

<script>
// pages/association/edit/edit.js
export default {
    data() {
        return {
            listObj: [],
            logoUrl: '',
            erweimaUrl: '',
            img: false
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        // count = options.count;
        // console.log(count);
        let args = uni.getStorageSync('args') ; // if (count != 'guest') {
        //   count = Number(options.count)
        // }

        let that = this;
        uni.showLoading({
            title: '查询中',
            mask: true,
            success: (result) => {
                // console.log(count);
                // db.collection('associationApply')
                //     .where({
                //         count,
                //         school: args.school
                //     })
                //     .get()
					uni.request({
							url:"",
							method:""
					}).then((res)=>{
						//eeeeeeeeee
					})
                    .finally(() => {
                        // console.log(res);
                        // let dataObj = res.data[0].hostMess;
                        // console.log(dataObj);
                        that.setData({
                            listObj: [
                                {
                                    title: '社团名称',
                                    value: "1111", //
                                    tap: 'warnTip',
                                    name: 'association'
                                },
                                {
                                    title: '管理员学号',
                                    value: "22222",
                                    tap: 'warnTip',
                                    name: 'card'
                                },
                                {
                                    title: '管理员姓名',
                                    value: "333333",
                                    tap: 'getHandle',
                                    name: 'name'
                                },
                                {
                                    title: '管理员电话',
                                    value: "44444",
                                    tap: 'getHandle',
                                    name: 'phone'
                                },
                                {
                                    title: '设立部门',
                                    value: "ssadasd",
                                    tap: 'getHandle',
                                    name: 'department'
                                },
                                {
                                    title: '社团介绍',
                                    value: "sadasdas",
                                    tap: 'getHandle',
                                    name: 'detail'
                                }
                            ],
                            // logoUrl: res.data[0].logoUrl,
                            // erweimaUrl: res.data[0].erweimaUrl || ''
                        });
                        uni.hideLoading();
                    });
            }
        }); // console.log(options.count);
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {},
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {},
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {},
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {},
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
		tap(e){
			if(e.currentTarget.id=="warnTip"){
				this.warnTip()
			}
		
			
		},
        change() {
			//上传图片
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
                                    this.setData({
                                        img: true,
                                        erweimaUrl: imgUrl
                                    });
                                }
                            });
                        });
                }
            }); // this.setData({erweimaUrl:})
        },

        // 不可修改警告
        warnTip() {
            uni.showToast({
                title: '不可修改',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {}
            });
        },

        // 提交表单
        formSubmit(e) {
			let args = {
				nickName:'xx用户',
				username:"xxxx"
			}
             args = uni.getStorageSync('args')||args;
            console.log(e);
            let newDate = e.detail.value;
            let that = this;
            uni.showLoading({
                title: '审核中',
                mask: true,
                success: (result) => {
                    if ((newDate.association == that.listObj[0].value) == false) {
                        uni.hideLoading();
                        uni.showModal({
                            title: '警告',
                            content: '社团名称不可修改,需要修改请联系负责人。是否继续提交其余修改内容',
                            showCancel: true,
                            cancelText: '取消',
                            cancelColor: '#000000',
                            confirmText: '确定',
                            confirmColor: '#3CC51F',
                            success: (result) => {
                                if (result.confirm) {
                                    // 提交
                                    search(newDate);
                                }
                            }
                        });
                    } else {
                        search(newDate);
                    }

                    function search(newDate) {
                        let card = newDate.card == that.listObj[1].value;
                        let name = newDate.name == that.listObj[2].value;
                        let phone = newDate.phone == that.listObj[3].value;
                        let detail = newDate.detail == that.listObj[4].value; // let that=this

                        if (card == true && name == true && phone == true && detail == true) {
                            console.log(newDate);
                            uni.hideLoading();
                            uni.showToast({
                                title: '成功',
                                icon: 'none',
                                image: '',
                                duration: 1500,
                                mask: false,
                                success: (result) => {
                                    console.log(newDate);
                                    db.collection('associationApply')
                                        .where({
                                            count: count,
                                            school: args.school
                                        })
                                        .update({
                                            data: {
                                                hostMess: {
                                                    association: that.listObj[0].value,
                                                    card: that.listObj[1].value,
                                                    name: newDate.name,
                                                    phone: newDate.phone,
                                                    detail: newDate.detail,
                                                    department: newDate.department
                                                },
                                                erweimaUrl: that.erweimaUrl
                                            }
                                        })
                                        .then((res) => {
                                            console.log(res);
                                            uni.hideLoading();
                                            uni.showToast({
                                                title: '修改成功',
                                                icon: 'none',
                                                image: '',
                                                duration: 1500,
                                                mask: false,
                                                success: (result) => {}
                                            });
                                        });
                                }
                            });
                        } else {
                            console.log(newDate, 2122122121);
                            db.collection('associationApply')
                                .where({
                                    count: count,
                                    school: args.school
                                })
                                .update({
                                    data: {
                                        hostMess: {
                                            association: that.listObj[0].value,
                                            card: that.listObj[1].value,
                                            name: newDate.name,
                                            phone: newDate.phone,
                                            detail: newDate.detail,
                                            department: newDate.department
                                        },
                                        erweimaUrl: that.erweimaUrl
                                    }
                                })
                                .then((res) => {
                                    console.log(res);
                                    uni.hideLoading();
                                    uni.showToast({
                                        title: '修改成功',
                                        icon: 'none',
                                        image: '',
                                        duration: 1500,
                                        mask: false,
                                        success: (result) => {}
                                    });
                                });
                        }
                    }
                }
            }); // console.log(e);
        }
    }
};
</script>
<style>
@import './edit.css';
</style>

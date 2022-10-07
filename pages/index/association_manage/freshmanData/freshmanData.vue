<template>
    <view>
        <view class="item_wrap text" @tap="getExcel" style="display: flex; align-items: center; justify-content: center">点我导出全部数据</view>

        <view class="wrap">
            <view class="caption" v-if="freshData[0] != undefined" style="display: flex; align-items: center; justify-content: center">
                <view>招新数据</view>
                <view style="position: absolute; right: 30rpx" v-if="!isMoreCheck" @tap="isMoreCheckFun">多选发送信息</view>
                <view style="position: absolute; right: 30rpx" v-if="isMoreCheck" @tap="isComplate">完成</view>
            </view>
            <view class="item_wrap" :data-item="item" v-for="(item, index) in freshData" :key="index">
                <!-- <view class="item_wrap" data-item="{{item}}" bindtap="godetail"> -->

                <view class="from" :data-item="item" @tap="godetail">来自{{ item.username }}的萌新申请</view>

                <view class="status" :data-item="item" @tap="godetail" :style="'color:' + (item.read == false ? '#63A1A0' : '') + ';'">
                    {{ item.read == false ? '未读' : '已读' }} >
                </view>

                <view class="status" style="margin-left: 20rpx; color: #71c7ff">
                    <!-- {{item.read==false?'已发送':'发送'}} -->
                    {{ item.status }}
                </view>

                <checkbox v-if="isMoreCheck" class="round" checked="" :value="item" :data-item="item" @tap="input"></checkbox>

                <!-- </view> -->
            </view>
            <view class="tip">--没有更多啦--</view>
        </view>
    </view>
</template>

<script>
// pages/association/freshmanData/freshmanData.js
const db = wx.cloud.database();
let count = '';
export default {
    data() {
        return {
            freshData: [],
            isMoreCheck: false,
            moreCheckArr: [],
            goodlist: '',
            nowGoods: '',
            totalGoodsPri: '',
            undefined: ''
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function (options) {
        count = options.count;
        count = String(count);
        this.getDate(count);
    },
    onShow() {},
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
    /**
     * 生命周期函数--监听页面显示
     */

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
    onReachBottom: function () {
        this.getMore();
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {},
    methods: {
        checkboxChange(e) {
            console.log(e);
        },

        isComplate() {
            let checkDataAbc = this.moreCheckArr;
            let thisData = JSON.stringify(checkDataAbc);
            let checkData = JSON.parse(thisData);
            this.setData({
                isMoreCheck: false,
                moreCheckArr: []
            });
            console.log(checkData);

            if (checkData.length == 0) {
                uni.showToast({
                    title: '请选择信息',
                    icon: 'none'
                });
            } else {
                let data = JSON.stringify(checkData);
                uni.navigateTo({
                    url: '../sendMoreMsg/sendMoreMsg?checkData=' + data
                });
            }
        },

        //复选框
        input(e) {
            let item = e.currentTarget.dataset.item;
            let moreCheck = this.moreCheckArr;
            let data = JSON.stringify(moreCheck);
            let moreCheckArr = JSON.parse(data);
            console.log(moreCheckArr);

            if (moreCheckArr.length == 0) {
                moreCheckArr.push(item);
                let arr = new Array();
                arr = moreCheckArr;
                this.setData({
                    moreCheckArr: arr
                });
            } else {
                // 选中数组里面有数据
                console.log('进入第一个else');
                console.log(moreCheckArr.length);

                for (let i = 0; i < moreCheckArr.length; i++) {
                    console.log('进入for' + i);
                    console.log(moreCheckArr[i]);
                    var isInclude = JSON.stringify(moreCheckArr[i]) == JSON.stringify(item) ? true : false;
                    console.log(i + '是否包含' + isInclude);

                    if (isInclude == true) {
                        moreCheckArr.splice(i, 1);
                        console.log(isInclude);
                        console.log(moreCheckArr);
                        this.setData({
                            moreCheckArr
                        });
                        break;
                    }
                }

                if (isInclude == false) {
                    moreCheckArr.push(item);
                    console.log(isInclude);
                    console.log(moreCheckArr);
                    this.setData({
                        moreCheckArr
                    });
                }
            }

            console.log(moreCheckArr);
        },

        isMoreCheckFun() {
            this.setData({
                isMoreCheck: true
            });
        },

        //复选框
        userCheck: function (e) {
            let index = e.currentTarget.dataset.id; //获取用户当前选中的索引值

            let checkBox = this.goodlist;

            if (checkBox[index].checked) {
                this.goodlist[index].checked = false;
            } else {
                this.goodlist[index].checked = true;
            }

            this.setData({
                goodlist: this.goodlist
            });
            var reduArr = new Array(); //返回用户选中的值

            let value = checkBox.filter((item, index) => {
                if (item.checked == true) {
                    reduArr.push(item.value);
                }
            });
            let totalGoodsPri = this.calculatePrice(reduArr);
            this.setData({
                nowGoods: reduArr,
                totalGoodsPri
            });
            console.log(reduArr);
        },

        // 查询招新数据
        getDate(count) {
            console.log(count);
            uni.showLoading({
                title: '加载中'
            }).then((res) => {
                db.collection('assoFreshApply')
                    .where({
                        index: count
                    })
                    .get()
                    .then((res) => {
                        uni.hideLoading();
                        this.setData({
                            freshData: res.data
                        });
                    });
            });
        },

        // 触底加载
        getMore() {
            let dataLen = this.freshData.length;

            if (dataLen < 20) {
                uni.showToast({
                    title: '没有更多啦~',
                    icon: 'none',
                    image: '',
                    duration: 1500,
                    mask: false,
                    success: (result) => {}
                });
            } else {
                uni.showLoading({
                    title: '加载中'
                });
                db.collection('assoFreshApply')
                    .where({
                        index: count
                    })
                    .skip(dataLen)
                    .get()
                    .then((res) => {
                        if (res.data.length == 0) {
                            uni.showToast({
                                title: '没有更多啦~',
                                icon: 'none',
                                image: '',
                                duration: 1500,
                                mask: false,
                                success: (result) => {}
                            });
                        } else {
                            let freshData = this.freshData;
                            freshData.push(...res.data);
                            this.setData({
                                freshData
                            });
                            uni.hideLoading({
                                success: (res) => {}
                            });
                        }
                    });
            }
        },

        // 招新数据导出为excel
        getExcel() {
            let that = this;
            let excelData = this.freshData;
            console.log(excelData);
            uni.showLoading({
                title: '导出中'
            });

            try {
                wx.cloud
                    .callFunction({
                        name: 'associationSend',
                        data: {
                            type: 12,
                            index: count // excelData:excelData
                        }
                    })
                    .then((res) => {
                        console.log(res);
                        const fileID = res.result.fileID;
                        wx.cloud
                            .downloadFile({
                                fileID: fileID
                            })
                            .then((res) => {
                                if (res.statusCode == 200) {
                                    let index = this.freshData.length - 1;
                                    let aso = this.freshData[index].content.association[0].detail; // const manage = wx.getFileSystemManager();
                                    // manage.saveFile({
                                    //   tempFilePath: res.tempFilePath,
                                    //   filePath: wx.env.USER_DATA_PATH + `/${aso}招新数据.xlsx`,  // 文件重命名 可自定义
                                    //   success: function(res) {
                                    //   }
                                    // });

                                    const filePath = res.tempFilePath;
                                    uni.openDocument({
                                        filePath: filePath,
                                        showMenu: true,
                                        success: function (res) {
                                            console.log(res, '打开文档成功');
                                        }
                                    });
                                    uni.hideLoading({
                                        success: (res) => {}
                                    });
                                } else {
                                    uni.hideLoading({
                                        success: (res) => {
                                            uni.showToast({
                                                title: '导出错误，请稍后再试',
                                                icon: 'error'
                                            });
                                        }
                                    });
                                } // that.delCloudFile(fileID);

                                console.log(res);
                            });
                    });
            } catch (error) {
                console.log(error);
            }
        },

        testFunction() {
            wx.cloud
                .callFunction({
                    name: 'association',
                    data: {
                        action: 'testFunction',
                        data: {
                            testdata: '123'
                        }
                    }
                })
                .then((res) => {
                    console.log(res);
                });
        },

        //删除云存储文件
        delCloudFile(fileID) {
            console.log('进入delCloudFile');
            var fileIDs = [];
            fileIDs.push(fileID);
            console.log(fileIDs); //删除云存储中的excel文件

            wx.cloud.deleteFile({
                fileList: fileIDs,
                success: (res) => {
                    // handle success
                    console.log(res);
                },
                fail: (err) => {
                    console.log(err);
                }
            });
        },

        // 详情页
        godetail(e) {
            let content = e.currentTarget.dataset.item;
            content = JSON.stringify(content);
            uni.navigateTo({
                url: `/pages/association/freshmanDetail/freshmanDetail?content=${content}`
            }); // console.log(e.currentTarget.dataset.item);
        }
    }
};
</script>
<style>
@import './freshmanData.css';
</style>

<template>
    <view>
        <scroll-view class="wrap">
            <!-- 招新海报 -->
            <view class="top_wrap">
                <view class="add_caption">招新海报</view>
                <!-- <view class="img" bind:tap="uploadImg" wx:if="{{!imgUrl}}"> -->
                <view v-if="!imgUrl" class="img" @tap="uploadImg">
                    <image class="img-camera" src="/static/pages/association/img/Camera-3.png" />
                </view>
                <!-- </view> -->
                <view class="img_1" v-else>
                    <swiper :indicator-dots="true" indicator-color="#fff" indicator-active-color="red" :current="current">
                        <block>
                            <swiper-item @longpress="changeImg">
                                <image :data-src="imgUrl" :src="imgUrl" mode="aspectFill" style="z-index: 11; width: 100%; height: 100%" @tap="previewImage" />
                            </swiper-item>
                        </block>
                    </swiper>
                </view>
                <view class="title">长按修改</view>
            </view>
            <!-- 招新标题 -->
            <view class="add_man_wrap">
                <view class="add_caption">选择招新标题</view>
                <view class="inp_wrap">
                    <textarea
                        class="handle_wrap"
                        maxlength="20"
                        placeholder="xx社团/协会招新啦~"
                        @input="getTitle"
                        :value="add_title"
                        style="height: 150rpx"
                        :disabled="sendStatus"
                    ></textarea>
                </view>
            </view>

            <!-- 问卷编辑 -->
            <view class="mess_item">
                <view class="title">招新问卷[{{ sendStatus == false ? '未发布' : '已发布' }}]</view>
                <view class="content_item" v-for="(item, index) in freshman" :key="index">
                    <view class="name" style="display: flex; align-items: center">{{ index + 1 }}.{{ item.content }}</view>

                    <view class="operation">
                        <!-- <view class="delete" data-item="{{item}}" bind:tap="delete">删除</view> -->
                        <view v-if="!(item.content == '姓名' || item.content == '联系方式' || item.content == '学号')" class="delete" :data-item="item" @tap="deleteFun">删除</view>
                        <view v-if="!(item.content != '姓名' && item.content != '联系方式' && item.content != '学号')" class="delete" :data-item="item" @tap="tishi">默认</view>
                        <!-- <view class="write">编辑</view> -->
                    </view>
                </view>
                <view class="add" @tap="clickme">+</view>
            </view>

            <!-- 活动截至时间 -->
            <view class="add_man_wrap">
                <view class="add_caption">截至时间</view>
                <view class="changeTime">
                    <picker mode="date" :value="date" start="2022-2-01" end="2023-1-01" @change="changeDate" class="sends" :disabled="sendStatus">
                        {{ date == null ? '请选择截止时间' : '截至:' + date }}
                    </picker>
                </view>
                <!-- <view class="inp_wrap">
            <textarea class="handle_wrap" maxlength="-1" placeholder="xx社团/协会招新啦~" bind:input="getTitle" value="{{add_title}}" style="height: 200rpx;"></textarea>
        </view> -->
            </view>
            <!-- 发布按钮 -->
            <view class="bt_wrap">
                <view class="send" @tap="send" :style="sendStatus == false ? 'background-color: #63A1A0;' : 'background-color: #63A1A0;'">
                    {{ sendStatus == false ? '发布招新' : '撤回招新问卷' }}
                </view>

                <view v-if="sendStatus == false ? false : true" class="send" data-target="Image" @tap="showModalQR" style="background-color: cornflowerblue; margin-top: 24rpx">
                    生成邀请报名社团二维码
                </view>
            </view>
        </scroll-view>
        <view class="commodity_screen" @tap="hideModal" v-if="showModalStatus"></view>
        <!-- 弹出框 -->
        <view :animation="animationData" class="commodity_attr_box" v-if="showModalStatus">
            <view class="title">添加问题</view>
            <view class="input">
                <textarea maxlength="-1" placeholder="内容" @input="contentFun"></textarea>
            </view>
            <view class="bt_wrap">
                <view class="add" @tap="add_contentFun">添加</view>
            </view>
        </view>

        <!-- 二维码弹窗 -->

        <view :class="'cu-modal ' + (modalName == 'Image' ? 'show' : '')">
            <view class="cu-dialog">
                <image class="bg-img" :src="qrcodeimg" show-menu-by-longpress="1" style="height: 730rpx; width: 660rpx" mode="aspectFill">
                    <view class="cu-bar justify-end text-white">
                        <view class="action" @tap="hideModalQR">
                            <text class="cuIcon-close"></text>
                        </view>
                    </view>
                </image>

                <view class="cu-bar bg-white">
                    <view class="action margin-0 flex-sub solid-left" @tap="hideModalQR">长按图片即可保存，点我关闭</view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
let db = wx.cloud.database();
let count = ''; //学号arg的username

export default {
    data() {
        return {
            freshman: [],
            showModalStatus: false,
            add_content: '',
            sendStatus: false,

            //发布状态
            date: null,

            qrcodeimg: '',

            //邀请二维码
            clicksavetitle: false,

            school: '',
            assoMess: '',
            erweimaUrl: '',
            imgUrl: '',
            CoverHeight: '',
            CoverWidth: '',
            ShowHeight: '',
            add_title: '',
            clone_id: '',
            modalName: '',
            animationData: '',
            content: '',
            current: ''
        };
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let assoMess = JSON.parse(options.assoMess); //传入社团详情

        console.log(assoMess);
        console.log(options);
        let res = uni.getStorageSync('args');

        if (options.erweimaUrl != null && options.count != null) {
            var erweimaUrl = decodeURIComponent(options.erweimaUrl);
            count = options.count; // count = res

            console.log(erweimaUrl);
        } else {
            var erweimaUrl = 'https://s1.ax1x.com/2022/09/18/xpIY4I.jpg';
            count = String(res.username);
        }

        let school = res.school;
        let nickName = res.nickName;
        let id = Date.parse(new Date());
        uni.showLoading({
            title: '加载中',
            mask: true,
            success: (result) => {
                db.collection('associationMess')
                    .where({
                        count: count
                    })
                    .get()
                    .then((res) => {
                        console.log(res);

                        if (res.data.length == 0 || res.data.length == null) {
                            db.collection('associationMess')
                                .add({
                                    data: {
                                        count: count,
                                        school: school,
                                        nickName: nickName,
                                        freshman: [
                                            {
                                                content: '姓名',
                                                id: id
                                            },
                                            {
                                                content: '联系方式',
                                                id: id + 1
                                            },
                                            {
                                                content: '学号',
                                                id: id + 2
                                            }
                                        ],
                                        sendStatus: false,
                                        assoMess: assoMess,
                                        personArr: [],
                                        erweimaUrl: erweimaUrl
                                    }
                                })
                                .then((res) => {
                                    console.log(erweimaUrl);
                                    uni.hideLoading();
                                    this.setData({
                                        freshman: [
                                            {
                                                content: '姓名',
                                                id: id
                                            },
                                            {
                                                content: '联系方式',
                                                id: id + 1
                                            },
                                            {
                                                content: '学号',
                                                id: id + 2
                                            }
                                        ],
                                        sendStatus: false,
                                        school: school,
                                        assoMess: assoMess,
                                        erweimaUrl
                                    });
                                });
                            this.judge();
                        } else {
                            console.log(erweimaUrl);
                            db.collection('associationMess')
                                .where({
                                    count: count
                                })
                                .get()
                                .then((res) => {
                                    uni.hideLoading();
                                    console.log(res.data[0].title, '141');
                                    this.setData({
                                        freshman: res.data[0].freshman,
                                        sendStatus: res.data[0].sendStatus,
                                        imgUrl: res.data[0].imgUrl,
                                        CoverHeight: res.data[0].CoverHeight,
                                        CoverWidth: res.data[0].CoverWidth,
                                        ShowHeight: res.data[0].ShowHeight,
                                        school: school,
                                        assoMess: assoMess,
                                        add_title: res.data[0].Title,
                                        //渲染招新标题
                                        date: res.data[0].date,
                                        clone_id: res.data[0]._id,
                                        erweimaUrl
                                    });
                                    this.judge();
                                });
                        }
                    });
            }
        });
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
        // 生成邀请二维码
        showModalQR(e) {
            let _this = this;

            uni.showLoading({
                title: '生成中',
                mask: true
            });
            let index = count + '社团';
            let School = uni.getStorageSync('args').school;
            wx.cloud
                .callFunction({
                    name: 'associationSend',
                    data: {
                        type: 8,
                        index: index,
                        School: School
                    }
                })
                .then((res) => {
                    console.log(res);
                    let base64 = uni.arrayBufferToBase64(res.result);
                    console.log(base64 + '123');
                    let codeImg = 'data:image/PNG;base64,' + base64;
                    console.log(codeImg);

                    _this.setData({
                        qrcodeimg: codeImg // modalName: null
                    });

                    uni.hideLoading({
                        success: (res) => {}
                    });
                });
            this.setData({
                modalName: e.currentTarget.dataset.target
            });
        },

        tishi() {
            uni.showToast({
                title: '默认不可删',
                icon: 'none'
            });
        },

        judge() {
            console.log('运行judge');

            if (this.add_title) {
                console.log('进入if');
                this.setData({
                    clicksavetitle: true
                });
            }
        },

        hideModalQR() {
            this.setData({
                modalName: null
            });
        },

        // 弹窗
        clickme() {
            if (this.sendStatus == true) {
                uni.showModal({
                    title: '提示',
                    content: '已发布不可修改',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                        }
                    }
                });
            } else {
                this.showModal();
            }
        },

        //显示对话框
        showModal: function () {
            // 显示遮罩层
            var animation = uni.createAnimation({
                duration: 200,
                timingFunction: 'linear',
                delay: 0
            });
            this.animation = animation;
            animation.translateY(300).step();
            this.setData({
                animationData: animation.export(),
                showModalStatus: true
            });
            setTimeout(
                function () {
                    animation.translateY(0).step();
                    this.setData({
                        animationData: animation.export()
                    });
                }.bind(this),
                200
            );
        },

        //隐藏对话框
        hideModal: function () {
            // 隐藏遮罩层
            var animation = uni.createAnimation({
                duration: 200,
                timingFunction: 'linear',
                delay: 0
            });
            this.animation = animation;
            animation.translateY(300).step();
            this.setData({
                animationData: animation.export()
            });
            setTimeout(
                function () {
                    animation.translateY(0).step();
                    this.setData({
                        animationData: animation.export(),
                        showModalStatus: false
                    });
                }.bind(this),
                200
            );
        },

        // 输入内容
        contentFun(e) {
            this.setData({
                add_content: e.detail.value
            });
        },

        // 提交
        add_contentFun() {
            let content = this.add_content;
            let id = Date.parse(new Date());

            if (content == '') {
                uni.showToast({
                    title: '请输入内容',
                    icon: 'none',
                    image: '',
                    duration: 1500,
                    mask: false
                });
                return -1;
            } // if()
            else {
                let freshman = this.freshman;
                freshman.push({
                    content: content,
                    id: id
                });
                db.collection('associationMess')
                    .where({
                        count: count
                    })
                    .update({
                        data: {
                            freshman: freshman
                        }
                    })
                    .then((res) => {
                        uni.showToast({
                            title: '添加成功',
                            icon: 'none',
                            image: '',
                            duration: 1500,
                            mask: false,
                            success: (result) => {
                                this.setData({
                                    content: '',
                                    freshman: freshman
                                });
                                this.hideModal();
                            }
                        });
                    });
            }
        },

        // 删除内容
        deleteFun(e) {
            let id = e.currentTarget.dataset.item.id;
            let freshman = this.freshman;
            let newFreshman = [];

            for (let i = 0; i < freshman.length; i++) {
                if (freshman[i].id != id) {
                    newFreshman.push(freshman[i]);
                }
            }

            this.setData({
                freshman: newFreshman
            });
            uni.showToast({
                title: '删除成功',
                icon: 'none',
                image: '',
                duration: 1500,
                mask: false,
                success: (result) => {
                    db.collection('associationMess')
                        .where({
                            count: count
                        })
                        .update({
                            data: {
                                freshman: newFreshman
                            }
                        });
                }
            });
        },

        // 发布状态选择
        send() {
            //分为撤销问卷和发布两种情况
            if (!this.sendStatus) {
                if (!this.imgUrl) {
                    uni.showToast({
                        title: '您还未上传海报',
                        icon: 'none'
                    });
                    return -1;
                }

                if (!this.imgUrl) {
                    uni.showToast({
                        title: '您还未上传海报',
                        icon: 'none'
                    });
                    return -1;
                }

                if (!this.date) {
                    uni.showToast({
                        title: '您还未上传选择截至时间',
                        icon: 'none'
                    });
                    return -1;
                }

                if (!this.add_title) {
                    uni.showToast({
                        title: '您还未填写社团招新介绍~',
                        icon: 'none'
                    });
                    return -1;
                }

                if (this.freshman[0] === undefined) {
                    uni.showToast({
                        title: '您还未发布问题',
                        icon: 'none'
                    });
                    return -1;
                }
            }

            let sendStatus = this.sendStatus;

            if (sendStatus == false) {
                if (this.date == null) {
                    uni.showModal({
                        title: '提示',
                        content: '设置截至时间',
                        showCancel: true,
                        cancelText: '取消',
                        cancelColor: '#000000',
                        confirmText: '确定',
                        confirmColor: '#3CC51F'
                    });
                } else {
                    uni.showModal({
                        title: '提示',
                        content: '确定发布',
                        showCancel: true,
                        cancelText: '取消',
                        cancelColor: '#000000',
                        confirmText: '确定',
                        confirmColor: '#3CC51F',
                        success: (result) => {
                            if (result.confirm) {
                                uni.showLoading({
                                    title: '发布中...',
                                    mask: true,
                                    success: (result) => {
                                        wx.cloud
                                            .callFunction({
                                                name: 'associationSend',
                                                data: {
                                                    type: 0,
                                                    AllPhoto: [this.imgUrl],
                                                    Cover: this.imgUrl,
                                                    CoverHeight: this.CoverHeight,
                                                    CoverWidth: this.CoverWidth,
                                                    School: this.school,
                                                    ShowHeight: this.ShowHeight,
                                                    Title: this.add_title,
                                                    index: count + '社团',
                                                    endTime: this.date,
                                                    question: this.freshman,
                                                    association: this.assoMess,
                                                    count: count,
                                                    erweimaUrl: this.erweimaUrl
                                                }
                                            })
                                            .then((res) => {
                                                console.log(433);
                                                db.collection('associationMess')
                                                    .where({
                                                        count: count
                                                    })
                                                    .update({
                                                        data: {
                                                            sendStatus: true,
                                                            Title: this.add_title,
                                                            erweimaUrl: this.erweimaUrl
                                                        }
                                                    })
                                                    .then((res) => {
                                                        uni.hideLoading();
                                                        uni.showToast({
                                                            title: '发布成功',
                                                            icon: 'none',
                                                            image: '',
                                                            duration: 1500,
                                                            mask: false,
                                                            success: (result) => {}
                                                        });
                                                        this.setData({
                                                            sendStatus: true
                                                        });
                                                    })
                                                    .catch((e) => {
                                                        console.log(e, 404);
                                                    });
                                            })
                                            .catch((e) => {
                                                console.log(e, 333); //报错要catch捕获
                                            });
                                    }
                                });
                            }
                        }
                    });
                }
            } else {
                uni.showModal({
                    title: '提示',
                    content: '撤回招新问卷',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                            uni.showLoading({
                                title: '撤回中...',
                                mask: true,
                                success: (result) => {
                                    wx.cloud
                                        .callFunction({
                                            name: 'associationSend',
                                            data: {
                                                type: 1,
                                                count: count
                                            }
                                        })
                                        .then((res) => {
                                            uni.showToast({
                                                title: '已撤回',
                                                icon: 'none',
                                                image: '',
                                                duration: 1500,
                                                mask: false,
                                                success: (result) => {
                                                    this.setData({
                                                        sendStatus: false
                                                    });
                                                }
                                            });
                                        });
                                }
                            });
                        }
                    }
                });
            }
        },

        // 招新海报
        uploadImg() {
            let id = Date.parse(new Date());
            uni.showLoading({
                title: '上传中'
            });
            uni.chooseImage({
                count: 1,
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'],
                success: (res) => {
                    wx.cloud
                        .uploadFile({
                            cloudPath: id + '/static/pages/association/freshman/.png',
                            filePath: res.tempFilePaths[0]
                        })
                        .then((res) => {
                            let imgUrl = res.fileID; // 计算图片

                            uni.getImageInfo({
                                src: imgUrl,
                                success: (res) => {
                                    uni.showToast({
                                        title: '上传成功',
                                        icon: 'none'
                                    });
                                    let height = res.height > 500 ? 500 : res.height;
                                    let width = res.width < 370 ? 370 : res.width;
                                    let CoverHeight = height + 'rpx';
                                    let CoverWidth = width;
                                    let ShowHeight = height;
                                    db.collection('associationMess')
                                        .where({
                                            count: count
                                        })
                                        .update({
                                            data: {
                                                imgUrl: imgUrl,
                                                CoverHeight,
                                                CoverWidth,
                                                ShowHeight
                                            }
                                        })
                                        .then((res) => {
                                            uni.showToast({
                                                title: '上传成功',
                                                icon: 'none',
                                                image: '',
                                                duration: 1500,
                                                mask: false,
                                                success: (result) => {
                                                    console.log('成功');
                                                    this.setData({
                                                        imgUrl: imgUrl
                                                    });
                                                }
                                            });
                                        });
                                }
                            });
                        });
                }
            });
        },

        // 加载图片
        previewImage(e) {
            uni.previewImage({
                current: e.currentTarget.dataset.src,
                urls: [e.currentTarget.dataset.src],
                success: (result) => {}
            });
        },

        // 删除海报
        changeImg() {
            if (this.sendStatus == true) {
                uni.showModal({
                    title: '提示',
                    content: '已发布不可修改',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                        }
                    }
                });
            } else {
                this.uploadImg();
            }
        },

        // 选择时间
        changeDate(e) {
            if (this.sendStatus == true) {
                uni.showModal({
                    title: '提示',
                    content: '招新信息已发布,不可修改',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                        }
                    }
                });
            } else {
                db.collection('associationMess')
                    .where({
                        count: count
                    })
                    .update({
                        data: {
                            date: e.detail.value
                        }
                    })
                    .then((res) => {
                        this.setData({
                            date: e.detail.value
                        });
                    });
            }
        },

        // 招新标题
        getTitle(e) {
            this.setData({
                add_title: e.detail.value
            });
        },

        // 确认修改标题
        add_titleFun() {
            if (this.add_title == '') {
                uni.showToast({
                    title: '请输入标题',
                    icon: 'none',
                    image: '',
                    duration: 1500,
                    mask: false,
                    success: (result) => {}
                });
            } else if (this.sendStatus == true) {
                uni.showModal({
                    title: '提示',
                    content: '发布后不可修改',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                        }
                    }
                });
            } else {
                uni.showLoading({
                    title: '修改中',
                    mask: true,
                    success: (result) => {
                        db.collection('associationMess')
                            .where({
                                count: count
                            })
                            .update({
                                data: {
                                    Title: this.add_title
                                }
                            })
                            .then((res) => {
                                this.setData({
                                    clicksavetitle: true
                                });
                                uni.showToast({
                                    title: '修改成功',
                                    icon: 'none',
                                    image: '',
                                    duration: 1500,
                                    mask: false,
                                    success: (result) => {
                                        uni.hideLoading();
                                    }
                                });
                            });
                    }
                });
            }
        }
    },
    watch: {
        _id: {
            handler: function (newVal, oldVal) {
                this.clone_id = newVal;
            },

            immediate: true
        }
    }
};
</script>
<style>
@import './freshman.css';
</style>

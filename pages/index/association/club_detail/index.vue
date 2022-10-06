<template>
    <view>
        <view class="page" v-if="modalName == ''">
            <view class="club">
                <image class="bd" mode="aspectFill" @tap="showModal" id="bd_img" :src="club.imageurl"></image>

                <view class="club_head">
                    <image mode="aspectFill" :src="club.imageurl" @tap="showModal" class="head_img"></image>
                    <view>{{ club.roomname }}</view>
                </view>
                <view class="ke_detail">
                    <view class="content">{{ club.introduce }}</view>
                </view>
            </view>
        </view>

        <view class="show" v-else-if="">
            <image mode="widthFix" :src="modalName" @tap="hideModal"></image>
        </view>
        <view @tap="toCreate_Club" class="new_club">查看群号</view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            modalName: '',
            club: {
                imageurl: '',
                roomname: '',
                introduce: ''
            }
        };
    }
    /**
     * 生命周期函数--监听页面加载
     */,
    onLoad: function () {
        //console.log(app.globalData.club, 1111)
        var that = this; // that.setData({
        //   club: app.globalData.club
        // })

        uni.getStorage({
            key: 'club',

            success(res) {
                var data = res.data;
                var club = {
                    id: data.association_id,
                    erweimaUrl: data.erweimaUrl,
                    imageurl: data.imageurl,
                    roomname: data.roomname,
                    introduce: data.introduce
                };
                that.setData({
                    club: club
                });
            }
        });
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {},
    methods: {
        toCreate_Club() {
            if (!this.club.erweimaUrl) {
                uni.showToast({
                    title: '该社团/机构还未上传群二维码',
                    icon: 'none'
                });
            } else {
                uni.previewImage({
                    urls: [this.club.erweimaUrl],
                    current: this.club.erweimaUrl
                });
            }

            console.log(333);
        },

        //本地预览图片式加载不出的，只有网络图片加载得了
        previewImg: function (e) {
            var photo;

            if (e.currentTarget.id == 'bd_img') {
                var photo = [this.club.bd_img];
            } else {
                var photo = [this.club.imageurl];
            }

            uni.previewImage({
                current: photo,
                urls: photo,
                success: function (res) {},
                fail: function (res) {},
                complete: function (res) {}
            });
        },

        showModal(e) {
            this.setData({
                modalName: this.club.imageurl
            });
        },

        hideModal(e) {
            this.setData({
                modalName: ''
            });
        }
    }
};
</script>
<style>
@import './index.css';
</style>

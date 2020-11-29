<template>
    <div class="sidebar2-container">
        <el-col :span="8">
            <!--<el-menu
                    default-active="2"
                    class="el-menu-vertical-demo">
                <el-menu-item-group>
                    <el-menu-item index="1-1">任务1</el-menu-item>
                    <el-menu-item index="1-2">任务1</el-menu-item>
                    <el-menu-item index="1-3">任务1</el-menu-item>
                    <el-menu-item index="1-4">任务1</el-menu-item>
                    <el-menu-item index="1-5">任务1</el-menu-item>
                    <el-menu-item index="1-6">任务2</el-menu-item>
                    <el-menu-item index="1-7">任务2</el-menu-item>
                    <el-menu-item index="1-8">任务1</el-menu-item>
                    <el-menu-item index="1-9">任务2</el-menu-item>
                    <el-menu-item index="1-10">任务2</el-menu-item>
                    <el-menu-item index="1-11">任务1</el-menu-item>
                    <el-menu-item index="1-12">任务2</el-menu-item>
                    <el-menu-item index="1-13">任务2</el-menu-item>
                    <el-menu-item index="1-14">任务1</el-menu-item>
                    <el-menu-item index="1-15">任务2</el-menu-item>
                    <el-menu-item index="1-16">任务2</el-menu-item>
                </el-menu-item-group>
            </el-menu>-->
            <el-menu
                    default-active="2"
                    class="el-menu-vertical-demo"
                    >
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span slot="title">导航一</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item v-for="i in tableData" :key="keys" :item="i.str" @click="print">{{i.str}}</el-menu-item>
                        <el-menu-item index="1-1" style="padding-right: 20px; padding-left:40px;width: 100%">选项1</el-menu-item>
                        <el-menu-item index="1-2"style="padding-right: 20px; padding-left:40px;width: 100%">选项2</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span slot="title">导航二</span>
                    </template>
                </el-submenu>
            </el-menu>
            <!--<el-table
                    :data="tableData"
                    height="450px"
                    border
                    style="width: 100%">
                <el-table-column
                        prop="date"
                        label="日期"
                        width="180">
                </el-table-column>
            </el-table>-->
            <!--<el-menu
                    default-active="2"
                    class="el-menu-vertical-demo"
                    @open="handleOpen"
                    @close="handleClose">
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>任务列表一</span>
                    </template>
                    <el-menu-item-group>
                        <template slot="title">分组一</template>
                        <el-menu-item index="1-1">任务1</el-menu-item>
                        <el-menu-item index="1-2">任务2</el-menu-item>
                    </el-menu-item-group>
                    <el-menu-item-group title="分组2">
                        <el-menu-item index="1-3">任务3</el-menu-item>
                    </el-menu-item-group>
                    <el-submenu index="1-4">
                        <template slot="title">任务4</template>
                        <el-menu-item index="1-4-1">啊这1</el-menu-item>
                        <el-menu-item index="1-4-1">啊这2</el-menu-item>
                    </el-submenu>
                    <el-submenu index="1-4">
                        <template slot="title">任务4</template>
                        <el-menu-item index="1-4-1">啊这1</el-menu-item>
                        <el-menu-item index="1-4-1">啊这2</el-menu-item>
                    </el-submenu>
                    <el-submenu index="1-4">
                        <template slot="title">任务4</template>
                        <el-menu-item index="1-4-1">啊这1</el-menu-item>
                        <el-menu-item index="1-4-1">啊这2</el-menu-item>
                    </el-submenu>
                    <el-submenu index="1-4">
                        <template slot="title">任务4</template>
                        <el-menu-item index="1-4-1">啊这1</el-menu-item>
                        <el-menu-item index="1-4-1">啊这2</el-menu-item>
                    </el-submenu>
                </el-submenu>
                <el-submenu index="5">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>任务列表二</span>
                    </template>
                    <el-menu-item-group>
                        <template slot="title">分组2</template>
                        <div class="item-container">
                            <el-menu-item index="5-1" style="padding-left: 0px;padding-right: 0px" >任务1</el-menu-item>
                        </div>
                        <el-menu-item index="5-2">任务2</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
            </el-menu>-->
        </el-col>
    </div>
</template>

<script>
    export default {
        name: "Sidebar2",
        data() {
            return {
                tableData: [{
                    keys: 1,
                    str: '2016-05-03',
                },  {
                    keys: 2,
                    str: '2016-05-06',
                }, {
                    keys: 3,
                    str: '2016-05-07',
                },{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},{keys: 3,str: '2016-05-07',},]
            }
        },
        methods:{
            print:function () {
                console.log("QQQQQQQQQQQQQQQ")
            }
        }
    }
</script>

<style scoped>
    .sidebar2-container {
        background: #000;
        width: 65.5%;
        top: 0;
        height: 100%;
    }

</style>

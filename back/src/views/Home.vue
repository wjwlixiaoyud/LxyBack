<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 20px">
      <el-col :span="6">
        <el-card>
          <div style="color: #409EFF">用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #F56C6C">销售总量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            $ 3000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #67C23A">收益总额</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            $ 30000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #909399">门店数量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            20
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <div id="main" style="width:500px;height: 400px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie"  style="width:500px;height: 400px"></div>
      </el-col>
    </el-row>



  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name:"Home",
  data(){
    return{

    }
  },
  //页面元素渲染之后再触发
  mounted() {
    var option;

    option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '趋势图',
        left:'center'
      },
      tooltip: {
        trigger: 'item'
      },
      xAxis: {
        data: ["第一季度","第二季度","第三季度","第四季度"],
        type: 'category',
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    var pieoption = {
      title: {
        text: '各季度会员数量统计',
        subtext: '统计图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '会员数量',
          type: 'pie',
          radius: '60%',
          data: [],//饼状图需要数据
          label : {
            normal : {
              formatter: '{d}%',
              textStyle : {
                fontWeight : 'normal',
                fontSize : 15
              }
            }
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };


    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    this.request.get("/echarts/membera").then(res=>{
      // option.xAxis.data=res.data.x
      option.series[0].data=res.data
      option.series[1].data=res.data
      myChart.setOption(option);

      pieoption.series[0].data=[
        {name:"第一季度" ,value:res.data[0]},
        {name:"第二季度" ,value:res.data[1]},
        {name:"第三季度" ,value:res.data[2]},
        {name:"第四季度" ,value:res.data[3]},
      ]
      pieChart.setOption(pieoption)
    })

  }
}
</script>


<style>

</style>
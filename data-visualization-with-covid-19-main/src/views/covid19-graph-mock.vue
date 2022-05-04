<template>

    <div>
<!--        <canvas id="tutorial" style="width: 100%; height: 100%;"></canvas>-->
        <canvas id="tutorial"></canvas>
<!--        <router-link to="/"><el-button type="info" round>返回首页</el-button></router-link>-->
    </div>


</template>

<script>
    import covid19Service from "../api/covid19";

    export default {
        name: "covid19-graph-mock",
        data() {
          return {
              graphMock: {
                  city: {
                      centerX: 0,
                      centerY: 0,
                  },
                  hospital: {
                      bed: [],
                      x: 0,
                      y: 0,
                      width: 0,
                      height: 0,
                  },
                  personPool: {
                      personList: []
                  },
                  peopleInfo: {
                      confirmedNum: 0,
                      deathNum:	0,
                      emptyBed: 0,
                      freezeNum: 0,
                      needBed: 0,
                      normalNum: 0,
                      shadowNum: 0,
                      total: 0,
                      worldTime: 0,
                  }
              }
          }
        },
        methods: {
            initDraw(){
                var canvas = document.getElementById('tutorial');
                console.info(window.innerHeight);
                // console.info(canvas);
                if (canvas.getContext) {
                    canvas.width = window.innerWidth;
                    canvas.height = window.innerHeight;
                    var ctx = canvas.getContext('2d');
                    ctx.clearRect(0, 0, canvas.width, canvas.height);
                    ctx.fillStyle = "#444444";
                    ctx.fillRect(0, 0, canvas.width, canvas.height);
                    return ctx;
                }
            },
            draw(ctx){
                ctx.clearRect(0, 0, window.innerWidth, window.innerHeight);
                ctx.fillStyle = "#444444";
                ctx.fillRect(0, 0, window.innerWidth, window.innerHeight);
                let hospital = this.graphMock.hospital;
                this.drawFont(ctx,"#00ff00","医院：",hospital.x-3,hospital.y-15);
                this.drawRect(ctx,hospital.x-3,hospital.y-3,hospital.width,hospital.height,"#00ff00");

                let personList = this.graphMock.personPool.personList;
                for (let i = 0;i < personList.length; i++){
                    this.drawPoint(ctx,personList[i].color,personList[i].x,personList[i].y);
                }
                // this.drawRect(ctx,400,400);
            },
             drawPoint(ctx,color,x,y){
                 //绘制点
                 ctx.beginPath();
                 ctx.fillStyle = color;
                 ctx.arc(x,y,1,0,Math.PI*2,true);
                 ctx.closePath();
                 ctx.fill();
            },
            drawRect(ctx,x,y,w,h,color){
                ctx.strokeStyle = color;
                ctx.strokeRect(x, y, w, h);
            },
            drawFont(ctx,color,text,x,y){
                // 设置字体
                ctx.font = "18px bold 微软雅黑";
                // 设置颜色
                ctx.fillStyle = color;
                // 设置水平对齐方式
                ctx.textAlign = "left";
                // 设置垂直对齐方式
                ctx.textBaseline = "middle";
                // 绘制文字（参数：要写的字，x坐标，y坐标）
                ctx.fillText(text, x, y);
            },
            mockInfo(ctx){
                let captionStartOffsetX = 1100 + this.graphMock.hospital.width + 40;
                let captionStartOffsetY = 40;
                let captionSize = 20;
                this.drawFont(ctx,"#FFFFFF","城市总人数："+this.graphMock.personPool.personList.length,captionStartOffsetX,captionStartOffsetY);
                this.drawFont(ctx,"#DDDDDD","健康者人数："+this.graphMock.peopleInfo.normalNum,captionStartOffsetX,captionStartOffsetY+captionSize);
                this.drawFont(ctx,"#FFEE00","潜伏期人数："+this.graphMock.peopleInfo.shadowNum,captionStartOffsetX,captionStartOffsetY+2*captionSize);
                this.drawFont(ctx,"#FF0000","发病者人数："+this.graphMock.peopleInfo.confirmedNum,captionStartOffsetX,captionStartOffsetY+3*captionSize);
                this.drawFont(ctx,"#48FFFC","已隔离人数："+this.graphMock.peopleInfo.freezeNum,captionStartOffsetX,captionStartOffsetY+4*captionSize);
                this.drawFont(ctx,"#00FF00","空余病床："+this.graphMock.peopleInfo.emptyBed,captionStartOffsetX,captionStartOffsetY+5*captionSize);
                this.drawFont(ctx,"#E39476","急需病床："+this.graphMock.peopleInfo.needBed,captionStartOffsetX,captionStartOffsetY+6*captionSize);
                this.drawFont(ctx,"#ccbbcc","病死人数："+this.graphMock.peopleInfo.deathNum,captionStartOffsetX,captionStartOffsetY+7*captionSize);
                this.drawFont(ctx,"#ffffff","世界时间（天）："+this.graphMock.peopleInfo.worldTime,captionStartOffsetX,captionStartOffsetY+8*captionSize);
            },
            mockGraph(){
                let self = this
                covid19Service.getMockData().then((res) => {
                    if (!res) {
                        console.log('错误:' + res)
                        return
                    }
                    this.graphMock = res;
                })
            }

        },
        mounted() {
            let self = this;
            let ctx = this.initDraw();

            // 定义定时器，隔 0.2 秒刷新一次
            this.timer = setInterval(() => {
                self.mockGraph();
                this.draw(ctx);
                this.mockInfo(ctx);
            },  200);

            this.$once('hook:beforeDestroy', () => {
                clearInterval(this.timer);
            });

        }
    }
</script>

<style scoped>

</style>
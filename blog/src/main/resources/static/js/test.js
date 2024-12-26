let cur_map = document.getElementById("map_container");
const scaleInfo = d3.select('#scale-info');
const centerInfo = d3.select('#center-info');

const storeData = [
	{name: 'AAA', lat: 36.3355809, lon: 127.4116839},
	{name: 'BBB', lat: 36.35245191970849, lon: 127.4094178197085},	
]

window.onload = function() {
    drawMap('#map_container', '/js/korea.json', 5500, -11900, 4050);
};

//지도 그리기
function drawMap(target, jsonPath, initialScale, initialX, initialY) {
    var width = 700; //지도의 넓이
    var height = 700; //지도의 높이
    var labels;
    var makers;

    var projection = d3.geo
        .mercator()
        .scale(initialScale)
        .translate([initialX, initialY]);
    var path = d3.geo.path().projection(projection);
    var zoom = d3.behavior
        .zoom()
        .translate(projection.translate())
        .scale(projection.scale())
        .scaleExtent([height, 800 * height])
        .on('zoom', zoom);

    var svg = d3
        .select(target)
        .append('svg')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .attr('id', 'map')
        .attr('class', 'map');

    var states = svg
        .append('g')
        .attr('id', 'states')
        .call(zoom);

    states
        .append('rect')
        .attr('class', 'background')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .attr('fill', '#8eecee');

    //geoJson데이터를 파싱하여 지도그리기
    d3.json(jsonPath, function(json) {
        states
            .selectAll('path') //지역 설정
            .data(json.features)
            .enter()
            .append('path')
            .attr('d', path)
            .attr('data-value', function(d) {
				return d.properties.val;
			})
            .attr('id', function(d) {
                return d.properties.eng_name;
            })
            .attr('initialScale', function(d) {
                return d.properties.initialScale;
            })
            .attr('initialX', function(d) {
                return d.properties.initialX;
            })
            .attr('initialY', function(d) {
                return d.properties.initialY;
            });

        labels = states
            .selectAll('text')
            .data(json.features) //라벨표시
            .enter()
            .append('text')
            .attr('transform', translateTolabel)
            .attr('id', function(d) {
                return d.properties.eng_name;
            })
            .attr('text-anchor', 'middle')
            .attr('dy', '.35em')
            .text(function(d) {
                return d.properties.kor_name;
            });
            
		makers = states
            .selectAll('.maker')
            .data(storeData)
            .enter()
            .append('circle')
            .attr('class', 'maker')
            .attr('fill', 'red')
            .attr('r', '5')
            .attr('transform', function(d){
				const coords = projection([d.lon, d.lat]);
				return 'translate(' + coords + ')';
			})

            
		setupRegionClickEvents(states, labels);
    });

    //텍스트 위치 조절 - 하드코딩으로 위치 조절을 했습니다.
    function translateTolabel(d) {
        var arr = path.centroid(d);
        if (d.properties.code == 31) {
            //서울 경기도 이름 겹쳐서 경기도 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / height + 20
                    : initialScale / height + 20;
        } else if (d.properties.code == 34) {
            //충남은 조금 더 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / height + 10
                    : initialScale / height + 10;
        }
        return 'translate(' + arr + ')';
    }

    function zoom() {
        const transform = d3.event.transform;
        projection.translate(d3.event.translate).scale(d3.event.scale);
        states.selectAll('path').attr('d', path);
        labels.attr('transform', translateTolabel);
        
        makers.attr('transform', function(d){
			const coords = projection([d.lon, d.lat]);

			return 'translate(' + coords +')';
		})
		
		scaleInfo.text('Scale: ' + d3.event.scale.toFixed(2))
		centerInfo.text('Center: (' + transform.x.toFixed(2) + ', ' + transform.y.toFixed(2) + ')');
    }
    
    function setupRegionClickEvents() {
	let click_val = document.querySelectorAll('path');
	
	click_val.forEach(p => 
	{
		p.addEventListener('click', function()
		{
			const value = this.getAttribute('data-value');
			
			if (value === "")
                alert('해당 지역은 준비중입니다.');
            else 
            {
				cur_map.remove();				
				cur_map = document.createElement('div');
				cur_map.id = 'map_container';
				document.body.appendChild(cur_map);
			
				const initialScale = this.getAttribute('initialScale');
				const initialX = this.getAttribute('initialX');
				const initialY = this.getAttribute('initialY');
			
				
                d3.json(value, function(json) 
                {
                    drawMap('#map_container', value, initialScale , initialX, initialY);
                });
            }
		})
	})
	}
}
const map_btn = document.getElementById("map_btn");
let cur_map = document.getElementById("map_container");

let states = null;
let path = null;

map_btn.onclick = function() 
{
	drawMap(cur_map);
};

//지도 그리기
function drawMap(target) 
{
    var width = 700; //지도의 넓이
    var height = 700; //지도의 높이
    var initialScale = 5500; //확대시킬 값
    var initialX = -11900; //초기 위치값 X
    var initialY = 4050; //초기 위치값 Y
    var labels;

    var projection = d3.geo
        .mercator()							// 투영법 설정
        .scale(initialScale)				// 초기 확대 비율
        .translate([initialX, initialY]);	// 지도 초기 위치
    
    path = d3.geo.path().projection(projection);
    
    var zoom = d3.behavior
        .zoom()
        .translate(projection.translate())		// 줌 초기 위치
        .scale(projection.scale())				// 초기 확대 비율
        .scaleExtent([height, 800 * height])	// 줌의 최대, 최소 확대 비율
        .on('zoom', zoom);						// 줌 이벤트 발생 시 호출될 함수

    var svg = d3
        .select(target)
        .append('svg')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .attr('id', 'map')
        .attr('class', 'map');

    states = svg
        .append('g')
        .attr('id', 'states')
        .call(zoom);

    states
        .append('rect')
        .attr('class', 'background')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .attr('fill', '#8eecee');	// 바다 쪽 색 바꾸기
	
	d3.json('/js/korea.json', function(json)
	{
		drawRegions(json);
		setupRegionClickEvents();
	});

    function zoom() 
    {				// 투영 업데이트	현재 이동값(드래그 시)		현재 확대 비율(줌 인, 아웃 시)
        projection.translate(d3.event.translate).scale(d3.event.scale);
        states.selectAll('path').attr('d', path);	// 경로 계산하고 다시 그림
        labels.attr('transform', translateTolabel);	// 지역명 계산된 위치로 이동
    }
}

function drawRegions(json)
{
	states
		.selectAll('path') //지역 설정
		.data(json.features)
		.enter()
		.append('path')
		.attr('d', path)
		.attr('data-value', function(d)
		{
			return d.properties.val;
		})
		.attr('id', function(d) 
		{
		    return d.properties.eng_name;
		});

	labels = states
		.selectAll('text')
		.data(json.features) //라벨표시
		.enter()
		.append('text')
		.attr('transform', translateTolabel)
		.attr('id', function(d)
		{
		    return 'label-' + d.properties.eng_name;
		})
		.attr('text-anchor', 'middle')
		.attr('dy', '.35em')
		.text(function(d) 
		{
		    return d.properties.kor_name;
		});
		
	//텍스트 위치 조절 - 하드코딩으로 위치 조절을 했습니다.
    function translateTolabel(d) 
    {
        var arr = path.centroid(d);
        
        if (d.properties.code == 31) 
        {
            //서울 경기도 이름 겹쳐서 경기도 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / 700 + 20
                    : 5500 / 700 + 20;
        } 
        else if (d.properties.code == 34) 
        {
            //충남은 조금 더 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / 700 + 10
                    : 5500 / 700 + 10;
        }
        
        return 'translate(' + arr + ')';
    }
}

function setupRegionClickEvents()
{
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
				states.selectAll('path').remove();
				states.selectAll('text').remove();
				
                d3.json(value, function(json) 
                {
                    drawRegions(json);
                });
            }
		})
	})
}
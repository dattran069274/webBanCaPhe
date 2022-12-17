var apiProvince = "https://provinces.open-api.vn/api/?depth=3"
var currentAdd = "";
// listProvince.onchange((e)=>console.log(e.value))
function getProvince(callback)
{
    fetch(apiProvince)
    .then((response)=>response.json())
    .then(callback)
}
getProvince(renderProvince)
function start()
{
    getProvince(renderProvince)
    renderDis
    // renderWards
}
start()
function renderProvince(prs)
{
    var listProvince = document.querySelector('.form-select__province')
    var htmls = prs.map((pr)=>{  
        
        return `
        <option class = "form-select__province-option" value="${pr.code}">${pr.name}</option>
        `
    })
    renderDis(prs[0].districts)
    renderWards(prs[0].districts[0].wards)
    const inputAdd = document.querySelector('.modal__container-form_province')
    inputAdd.value = prs[0].name +"/"+ prs[0].districts[0].name + "/"+prs[0].districts[0].wards[0].name;
    listProvince.innerHTML = htmls.join('')
    listProvince.onchange= function(event)
    {   
        var code = event.target.value
        prs.map((pr)=>
        {
            if(pr.code==code)
            {
                renderDis(pr.districts,pr.name)
                renderWards(pr.districts[0].wards)     
                inputAdd.value = pr.name+"/"+ pr.districts[0].name+"/"+pr.districts[0].wards[0].name;
                currentAdd = pr.name
            }
        })  
        
        // console.log(prs.event.target.value.districts)
    }

}
function renderDis(districts,pr)
{

    const inputAdd = document.querySelector('.modal__container-form_province')
    var listDis = document.querySelector('.form-select__dis')
    var htmls = districts.map((dis)=>{
        return `
        <option value="${dis.code}">${dis.name}</option>
        `
    })
    listDis.innerHTML = htmls.join('')
    listDis.onchange= function(event)
    {   
        var code = event.target.value
    
        districts.map((district)=>
        {
            if(district.code==code)
            {
                renderWards(district.wards)  
                
                inputAdd.value = currentAdd +"/"+ district.name+ "/" + district.wards[0].name;
                currentAdd = currentAdd +"/"+ district.name+ "/" 
                console.log(currentAdd)
            }
        })  
        
        // console.log(prs.event.target.value.districts)
    }
}
function renderWards(wards)
{
    var listWards = document.querySelector('.form-select__wards')
    var htmls = wards.map((ward)=>{
        return `
        <option value="${ward.code}">${ward.name}</option>
        `
    })
    listWards.innerHTML = htmls.join('')
    listWards.onchange= function(event)
    {   
        var code = event.target.value
    
        wards.map((ward)=>
        {
            if(ward.code==code)
            {
                
                inputAdd.value = currentAdd + ward.name;
            }
        })  
        
        // console.log(prs.event.target.value.districts)
    }
}
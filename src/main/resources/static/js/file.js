var apiProvince = "https://provinces.open-api.vn/api/?depth=3"
var finalAdd = "";
curPro = ""
curDis = ""
curWard = ""



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
    const inputAdd = document.querySelector('.modal__container-form_province')
    var listProvince = document.querySelector('.form-select__province')
    var htmls = prs.map((pr)=>{  
        
        return `
        <option class = "form-select__province-option" value="${pr.code}">${pr.name}</option>
        `
    })
    htmls.unshift(' <option class = "form-select__province-option" value="-1">--Chọn Tỉnh/Thành Phố--</option>')
    renderDis(prs[0].districts)
    renderWards(prs[0].districts[0].wards)
    listProvince.innerHTML = htmls.join('')
    listProvince.onchange= function(event)
    {   
        var code = event.target.value
        if(code==-1)
        {

        alert('Vui lòng nhập Tỉnh')  
        renderDis('')
        renderWards('')    
        inputAdd.value = ''  
        return     
        }
        else{
        prs.map((pr)=>
        {
         
            if(pr.code==code)
            {
                renderDis(pr.districts)
                renderWards(pr.districts[0].wards)        
                curPro = pr.name
            }
        }) 
        }
 
    
        // console.log(prs.event.target.value.districts)
    }

}
function renderDis(districts)
{
    const inputAdd = document.querySelector('.modal__container-form_province')

    var listDis = document.querySelector('.form-select__dis')
    var htmls
    if(districts==''||districts=='undefined'||districts==null)
    {
        htmls = '<option value="-1">--Chọn Quận/Huyện--</option>'
        listDis.innerHTML = htmls
    }
    else
    {
        htmls = districts.map((dis)=>{
            return `
            <option value="${dis.code}">${dis.name}</option>
            `
        })
        htmls.unshift('<option value="-1">--Chọn Quận/Huyện--</option>')
        listDis.innerHTML = htmls.join('')
    }
  

    // htmls.unshift('<option value=-1>--Chọn Quận/Huyện--</option>')
    
    listDis.onchange= function(event)
    {   
        var code = event.target.value
        if(code==-1)
        {  
            isCheck = false   
            alert('Vui lòng nhập Huyện')  
            renderWards('') 
            inputAdd.value=''     
            return   
        }
        else
        {
            districts.map((district)=>
            {
                if(district.code==code)
                {
                    renderWards(district.wards)     
                    
      
                   
                    curDis = district.name
                    
                }
            })
        }
      
        
        // console.log(prs.event.target.value.districts)
    }
}

function renderWards(wards)
{
    const inputAdd = document.querySelector('.modal__container-form_province')
    var listWards = document.querySelector('.form-select__wards')
    var htmls
    if(wards==''||wards=='undefined'||wards==null)
    {
        htmls = '<option value="-1">--Chọn Phường/Xã--</option>'
        listWards.innerHTML = htmls
    }
    else
    {
        htmls = wards.map((ward)=>{
            return `
            <option value="${ward.code}">${ward.name}</option>
            `
        })
        htmls.unshift('<option value="-1">--Chọn Phường/Xã--</option>')
        listWards.innerHTML = htmls.join('')
    }
    listWards.onchange= function(event)
    {   
        var code = event.target.value
        if(code==-1)
        {
            isCheck = 'false'
            alert('Vui Lòng Nhập Phường')
            return
        }
       
        else
        {
            wards.map((ward)=>
            {
                if(ward.code==code)
                {
                        curWard = ward.name
                        finalAdd = curPro + '/' +curDis + '/' + curWard
                        inputAdd.value = finalAdd  
                }
            }) 
        }
   
        
        // console.log(prs.event.target.value.districts)
    }
    
}


function getReversefinalAdd(){
    var list=finalAdd.split('/')
    return list[2]+','+list[1]+','+list[0];
}
const speAdd = document.querySelector('.modal__container-form_pro-spe')
var finalAddSpe = ''
  speAdd.onblur = (e)=>
  {
    if(e.target.value=='')
    {
        speAdd.value = ''
    }
    else
    {
        if(e.target.value==finalAddSpe)
        {
            return
        }
        else
        {
            finalAddSpe = e.target.value +','+ getReversefinalAdd(finalAdd)
            speAdd.value = e.target.value +','+ getReversefinalAdd(finalAdd)
        }
    }
  
  }

const logBut = document.querySelector('.navbar__user-dropdown')
const navbar__user = document.querySelector('.navbar__user-dropdown_list')
logBut.onclick = ()=>
{
    navbar__user.classList.toggle('active')
}
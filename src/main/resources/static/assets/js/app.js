var btnSuccess = document.querySelector('.control .success')
var btnWarning = document.querySelector('.control .warning')
var btnError = document.querySelector('.control .error')

btnSuccess.addEventListener('click', function () {
    createToast('success')
})
//btnWarning.addEventListener('click', function () {
//    createToast('warning')
//})
//btnError.addEventListener('click', function () {
//    createToast('error')
//})

function createToast(status) {
    let templateInner = `<i class="fa fa-check-circle" aria-hidden="true"></i>
                        <span class="message">Đã Thêm Vào Giỏ Hàng</span>`
    switch (status) {
        case 'success':
            templateInner = `<i class="fa fa-check-circle" aria-hidden="true"></i>
                        <span class="message">Đã Thêm Vào Giỏ Hàng</span>`
            break;
        case 'error':
            templateInner = `<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> 
            <span class="message">This is Error message</span>
                        `
            break;
        case 'warning':
            templateInner = `
             <i class="fa fa-info-circle" aria-hidden="true"></i>
             <span class="message">This is Waorning message</span>
                         `
            break;
    }

    var toast = document.createElement('div')
    toast.classList.add('toast')
    toast.classList.add(status)
    toast.innerHTML = `${templateInner}<span class="countdown"></span>`



    var toastList = document.getElementById('toasts')
    toastList.appendChild(toast)

    setTimeout(function () {
        toast.style.animation = 'slide_hide 2s ease forwards'
    }, 4000)

    setTimeout(function () {
        toast.remove()
    }, 6000)
}
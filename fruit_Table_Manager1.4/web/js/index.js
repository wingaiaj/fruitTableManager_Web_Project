function delfruit(fid) {
    if (confirm('是否确认删除?')) {
        window.location.href = 'del.do?fid=' + fid;
    }
}

function page(pageNo) {
    window.location.href = 'index.html?pageNo=' + pageNo;
}


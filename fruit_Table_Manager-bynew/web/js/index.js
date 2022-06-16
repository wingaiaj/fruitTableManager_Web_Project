function del(fid) {
    if (confirm('确认删除这条记录吗?')) {
        window.location.href = 'del.do?fid=' + fid;
    }
}

function page(pageNo) {
    window.location.href = 'index.html?pageNo=' + pageNo;
}
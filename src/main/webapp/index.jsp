<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<script src="https://code.jquery.com/jquery-3.1.1.js"></script>
<h2>Hello World!</h2>
<div id="data">Loading...</div>
<%--<br/>--%>
<%--<input type="button" id="sendUpdateToChannel" value="Отправить в канал"/>--%>
<script>
    $('#sendUpdateToChannel').on('click', function() {
        $.ajax('<%=request.getContextPath()%>/send.to.channel', {
            method: 'get'
        })
    });
    $.ajax('<%=request.getContextPath()%>/get.events', {
        method: 'get',
//        type: 'json',
        success: function(data, a, b) {
            var str = '';
            str += '<b>Nightfall:</b><br/><i>' + data.nightfall.name + '</i><br/>';
            var modifiers = data.nightfall.modifiers;
            var modL = modifiers.length;
            for (var i = 0; i < modL; i++) {
                str += modifiers[i] + "<br/>";
            }

            str += '<br/>';

            str += '<b>Heroic strike</b><br/><i>' + data.sivaHeroic.name + '</i><br/>';
            modifiers = data.sivaHeroic.modifiers;
            modL = modifiers.length;
            for (var i = 0; i < modL; i++) {
                str += modifiers[i] + "<br/>";
            }

            str += '<br/>';

            str += '<b>Weekly raid</b><br/><i>' + data.weeklyRaid.name + '</i><br/>';
            modifiers = data.weeklyRaid.modifiers;
            modL = modifiers.length;
            for (var i = 0; i < modL; i++) {
                str += modifiers[i] + "<br/>";
            }

            str += '<br/>';

            str += '<b>Weekly crucible</b><br/><i>' + data.weeklyCrucible + '</i><br/>';

            $('#data').html(str);
        }
    })
</script>
</body>
</html>

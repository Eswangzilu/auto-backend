# -*- coding: utf-8 -*
import requests
from lxml import etree
import time
import json
import pymysql
import smtplib
import chardet
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.image import MIMEImage
from email.header import Header
import datetime
from chinese_calendar import is_workday
import random
'''登录方法 传入账号密码返回cookie'''
def Cok(user,password):
    # 请求网站
    url = 'http://10.10.101.19:86/logincheck.php'
    
    # 请求头封装
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36',
        'Host': '10.10.101.19:86',
        'Origin': 'http://10.10.101.19:86',
        'Referer': 'http://10.10.101.19:86'
    }
    
    # 请求数据
    data = {
        'UNAME': user,
        'PASSWORD': password,
        'encode_type': '1'
    }
    cookie_jar = requests.post(url, headers=headers, data=data).cookies
    cookie_t = requests.utils.dict_from_cookiejar(cookie_jar)
    cookies = dict(cookie_t)
    return cookies

'''考勤页面详细信息返回函数 获取页面格式化数据后返回'''
def huoqu(user,password):
    url = 'http://10.10.101.19:86/general/attendance/personal/duty/'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36',
        'Host': '10.10.101.19:86',
        'Origin': 'http://10.10.101.19:86',
        'Referer': 'http://10.10.101.19:86'
    }
    cokee = Cok(user,password)
    r = requests.get(url, headers=headers, cookies=cokee)
    html = etree.HTML(r.text)
    name = html.xpath('//tr/td[1]/text()')
    Type = html.xpath('//tr/td[2]/text()')
    cg = html.xpath('//tr/td[6]/text()')  # .replace('\n','').replace(' ','')
    ttime = html.xpath('///tr/td[5]/text()')
    text = '登记类型 登记时间\n'
    for n, t, ttime, cg in zip(name, Type, ttime, cg):
        text = text + t + ' ' + ttime + '\n'
    return text

"打卡函数"
def daka(url,user,password):

    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36',
        'Host': '10.10.101.19:86',
        'Origin': 'http://10.10.101.19:86',
        'Referer': 'http://10.10.101.19:86'
    }
    cokee = Cok(user,password)
    requests.get(url, headers=headers, cookies=cokee)



'''判断节假日'''
def jjr(time):
    url = 'http://www.easybots.cn/api/holiday.php?d=' + str(time)
    r = requests.get(url)
    jd = json.loads(r.text)
    return jd[time]

def Get_Name(user,password):
    url = 'http://10.10.101.19:86/general/index.php?isIE=0&modify_pwd=0'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36',
        'Host': '10.10.101.19:86',
        'Origin': 'http://10.10.101.19:86',
        'Referer': 'http://10.10.101.19:86'
    }
    cokee = Cok(user, password)
    r = requests.get(url, headers=headers, cookies=cokee)
    html = etree.HTML(r.text)
    name = html.xpath('//*[@id="info_avater"]/div/div[1]/div[2]/h6/span/text()')
    return name
def huoqu2(user,password):

    url = 'http://10.10.101.19:86/general/attendance/personal/duty/'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36',
        'Host': '10.10.101.19:86',
        'Origin': 'http://10.10.101.19:86',
        'Referer': 'http://10.10.101.19:86'
    }
    cokee = Cok(user,password)
    r = requests.get(url, headers=headers, cookies=cokee)
    html = etree.HTML(r.text)
    err = html.xpath('/html/body/div/div/div[2]/text()')
    if err == ['用户未登录，请重新登录!']:
        return "用户未登录，请联系管理员或手动打卡"
    name = html.xpath('//tr/td[1]/text()')
    Type = html.xpath('//tr/td[2]/text()')
    cg = html.xpath('//tr/td[6]/text()')  # .replace('\n','').replace(' ','')
    ttime = html.xpath('///tr/td[5]/text()')
    text = '登记类型 登记时间\n'
    return cg


def adddate(con,nowdate):
    cur = con.cursor()
    cur.execute('select * from fb_daka where id=-1')
    data = cur.fetchall()
    if nowdate.__le__(data[0][6]):
        ifweekday = nowdate.isoweekday()
        if int(data[0][1]) == ifweekday:
            return True
        else:
            return False
    else:
        return is_workday(nowdate)


if __name__ == '__main__':
    zao = ""
    zhong = ""
    while True:
        try:
            tz = time.time()
            sj = time.strftime('%H-%M-%S', time.localtime(int(tz)))
            print(sj,"----")

            if sj == '00-00-30':
                date = datetime.datetime.now().date()
                con = pymysql.connect(host='10.30.59.136', port=3306, user='root', password='123456', database='wdr',
                                      charset='utf8')
                cur = con.cursor()
                cur.execute('select * from fb_daka where id != -1')
                if is_workday(date) or adddate(con,date):
                    zao = random.randint(40,59)
                    zhong = random.randint(10,20)
                    for i in cur.fetchall():
                        print('今天是节假日，暂不打卡')
#                        sendqqmail('今天是工作日，打卡继续', "正常打卡","2149610588@qq.com")
                else:
                    for i in cur.fetchall():
                        print('今天是节假日，暂不打卡')
#                        sendqqmail('今天是节假日，暂不打卡', "暂停打卡", "2149610588@qq.com")
                    time.sleep(74500)
            #print('今天正常打卡')
            if sj == '14-'+str(zhong)+'-20':
                # 连接到数据，获取链接对象
                con = pymysql.connect(host='10.30.59.136', port=3306, user='root', password='123456', database='wdr',
                                      charset='utf8')
                cur = con.cursor()
                cur.execute('select * from fb_daka where id != -1')
                for i in cur.fetchall():
                    if i[5] == 1:
                        daka('http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=3&USER_DUTP=1',i[2], i[3])
                        txt = huoqu(i[2], i[3])
#                        sendqqmail(txt,i[1],i[4])
                    elif i[5] == 0:
                        print("打卡已被停用")
#                        sendqqmail("打卡已被停用", "系统提示",i[4])

            elif sj == '07-'+str(zao)+'-00':
                con = pymysql.connect(host='10.30.59.136', port=3306, user='root', password='123456', database='wdr',
                                      charset='utf8')
                cur = con.cursor()
                cur.execute('select * from fb_daka where id != -1')
                for i in cur.fetchall():
                    if i[5] == 1:
                        daka('http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=1&USER_DUTP=1',i[2], i[3])
                        txt = huoqu(i[2], i[3])
#                        sendqqmail(txt,i[1],i[4])
                    elif i[5] == 0:
                        print("打卡已被停用")
#                        sendqqmail("打卡已被停用", "系统提示",i[4])
            elif sj == '08-05-00':
                con = pymysql.connect(host='10.30.59.136', port=3306, user='root', password='123456', database='wdr',
                                      charset='utf8')
                cur = con.cursor()
                cur.execute('select * from fb_daka where id != -1')
                for i in cur.fetchall():
                    if i[5] == 1:
                        cok = huoqu2(i[2], i[3])
                        if cok[0].replace("\n", '').replace(" ", '') == "已考勤":
                            print('考勤成功')
#                            sendqqmail("考勤成功，备用打卡暂不启用", "备用考勤成功提示","2149610588@qq.com")
                        else:
                            daka('http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=1&USER_DUTP=1',i[2], i[3])
                            txt = huoqu(i[2], i[3])
#                            sendqqmail(txt, i[1]+"备用打卡", i[4])

            elif sj == '14-30-00':
                con = pymysql.connect(host='10.30.59.136', port=3306, user='root', password='123456', database='wdr',
                                      charset='utf8')
                cur = con.cursor()
                cur.execute('select * from fb_daka where id != -1')
                for i in cur.fetchall():
                    if i[5] == 1:
                        cok = huoqu2(i[2], i[3])
                        if cok[3].replace("\n", '').replace(" ", '') == "已考勤":
                            print('考勤成功')
#                            sendqqmail("考勤成功，备用打卡暂不启用", "备用考勤成功提示","2149610588@qq.com")
                        else:
                            daka('http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=3&USER_DUTP=1',i[2], i[3])
                            txt = huoqu(i[2], i[3])
#                            sendqqmail(txt, i[1]+"备用打卡", i[4])
            time.sleep(1)
        except:
            print('打卡失败')
#            sendqqmail("打卡失败，请手动打卡","错误！！！","2149610588@qq.com")

    #     print(sj)
    #     if sj == '14:00:30':
    #         daka(url='http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=3&USER_DUTP=1')
    #         url = 'http://10.30.59.135:53392/send_friend_msg'
    #         txt = huoqu()
    #         data = {
    #             'bot':2088879986,
    #             'qq':1427304522,
    #             'msg':txt
    #         }
    #         requests.post(url,data=data)
    #         print('执行！')
    #     elif sj == '07-00-30':
    #         daka(url='http://10.10.101.19:86/general/attendance/personal/duty/submit.php?REGISTER_TYPE=1&USER_DUTP=1')
    #         url = 'http://10.30.59.135:53392/send_friend_msg'
    #         txt = huoqu()
    #         data = {
    #             'bot':2088879986,
    #             'qq':1427304522,
    #             'msg':txt
    #         }
    #         requests.post(url,data=data)
    #         print('执行！')
    #     time.sleep(1)
    # 'http://www.easybots.cn/api/holiday.php?d=20201031'

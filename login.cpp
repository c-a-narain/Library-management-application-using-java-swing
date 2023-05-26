#include <jni.h> // JNI header provided by JDK
#include <iostream>    // C++ standard IO header
#include <pqxx/pqxx>
#include <vector>
#include <string>
#include "signin.h"
#include "update.h"
#include "add.h"
#include "delete.h"
#include "borrow.h"
#include "returnback.h"
#include "viewAll.h"
#include "myBooks.h"
//#include "search.h"

using namespace pqxx;
using namespace std;

JNIEXPORT jboolean JNICALL Java_signin_checkadmin (JNIEnv* env, jobject thisObj, jstring usname, jstring pswd)
{
    try
    {
        const char* usname1 = env->GetStringUTFChars(usname, NULL);
        const char* pswd1 = env->GetStringUTFChars(pswd, NULL);

        std::string us = usname1;
        std::string ps = pswd1;

        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection connectionObject(connectionString.c_str());

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT id,email FROM ADMINS WHERE EMAIL='"+us+"' AND PASSWORD = crypt('" + ps + "', password) ");

        if (response.size() == 0) {
            return 0;
        }
        else {
            return 1;
        }
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }
    system("pause");

}

JNIEXPORT jboolean JNICALL Java_signin_checkuser (JNIEnv* env, jobject thisObj, jstring usname, jstring pswd) {
    try
    {
        const char* usname1 = env->GetStringUTFChars(usname, NULL);
        const char* pswd1 = env->GetStringUTFChars(pswd, NULL);

        std::string us = usname1;
        std::string ps = pswd1;

        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection connectionObject(connectionString.c_str());

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT id,email FROM users WHERE email = '"+us+"' AND password = crypt('"+ps+"', password)");

        if(response.size() == 0){
            return 0;        
        }
        else {
            return 1;
        }

    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }

    system("pause");

}

JNIEXPORT void JNICALL Java_update_updateBook(JNIEnv* env, jobject thisObj, jint bid, jint q) {
    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::work W(C);

        W.exec("UPDATE BOOKS set QUANTITY = " + std::to_string(q) + " where BOOK_ID=" + std::to_string(bid) + "");
        W.commit();
        std::cout << "Records updated successfully" << endl;
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
}

JNIEXPORT void JNICALL Java_add_addBook(JNIEnv* env, jobject thisObj, jint bid, jstring name, jstring author, jstring edd, jstring pub, jint q) {
    try {
        const char* name1 = env->GetStringUTFChars(name, NULL);
        const char* author1 = env->GetStringUTFChars(author, NULL);
        const char* edd1 = env->GetStringUTFChars(edd, NULL);
        const char* pub1 = env->GetStringUTFChars(pub, NULL);


        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::work W(C);
        W.exec("INSERT INTO BOOKS VALUES (" + std::to_string(bid) + ",'" + name1 + "','" + author1 + "','" + edd1 + "','" + pub1 + "'," + std::to_string(q) + "); ");

        W.commit();

        std::cout << "Records created successfully" << endl;
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
}

JNIEXPORT void JNICALL Java_delete_deleteBook (JNIEnv* env, jobject thisObj, jint bid)
{
    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::work W(C);
        W.exec("DELETE FROM BOOKS WHERE BOOK_ID=" + std::to_string(bid) + " ");
        W.commit();
        std::cout << "Records Deleted successfully" << endl;
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
}

JNIEXPORT void JNICALL Java_borrow_borrowBook(JNIEnv* env, jobject thisObj, jint bid, jstring username)
{
    int uid, quant;

    try
    {
        const char* name1 = env->GetStringUTFChars(username, NULL);

        string usname = name1;

        pqxx::connection connectionObject("host = localhost port = 5432 dbname = postgres user = postgres password = caNarain@2002");

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT * FROM user_login");

        for (size_t i = 0; i < response.size(); i++)
        {
            if (usname.compare(response[i][1].as<string>()) == 0)
            {
                uid = response[i][0].as<int>();
            }
        }
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }


    try {
        pqxx::connection connectionObject("host = localhost port = 5432 dbname = postgres user = postgres password = caNarain@2002");

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT * FROM books");

        for (size_t i = 0; i < response.size(); i++)
        {
            if (to_string(bid).compare(response[i][0].as<string>()) == 0)
            {
                quant = response[i][5].as<int>();
            }
        }
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }

    system("pause");
    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::work W(C);

        W.exec("INSERT INTO USER_DATA(book_id,user_id) VALUES(" + std::to_string(bid) + "," + std::to_string(uid) + ")");

        W.exec("UPDATE BOOKS set QUANTITY=QUANTITY-1 where BOOK_ID=" + std::to_string(bid) + " AND QUANTITY>-1;");
        W.commit();
        std::cout << "Books Borrowed successfully" << endl;
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }

}

JNIEXPORT void JNICALL Java_returnback_returnBook(JNIEnv* env, jobject thisObj, jint bid, jstring username)
{
    int uid;

    try
    {
        const char* name1 = env->GetStringUTFChars(username, NULL);

        string usname = name1;

        pqxx::connection connectionObject("host = localhost port = 5432 dbname = postgres user = postgres password = caNarain@2002");

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT * FROM user_login");

        for (size_t i = 0; i < response.size(); i++)
        {
            if (usname.compare(response[i][1].as<string>()) == 0)
            {
                uid = response[i][0].as<int>();
            }
        }
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }

    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::work W(C);

        /* Execute SQL query */
        W.exec("DELETE FROM USER_DATA WHERE BOOK_ID=" + std::to_string(bid) + " AND USER_ID=" + std::to_string(uid) + " ");
        W.exec("UPDATE BOOKS set QUANTITY = QUANTITY+1 WHERE BOOK_ID=" + std::to_string(bid) + " ");
        W.commit();
        std::cout << "Records Deleted successfully" << endl;
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
}

JNIEXPORT jstring JNICALL Java_viewAll_viewBooks(JNIEnv* env, jobject thisObj) {
    //vector<string> sv;

    jstring s=NULL;
    string glist;
    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection C(connectionString.c_str());

        if (C.is_open()) {
            std::cout << "Opened database successfully: " << C.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }
        pqxx::work W(C);

        pqxx::result response = W.exec("SELECT * FROM books ORDER BY BOOK_ID");
        
        for (size_t i = 0; i < response.size(); i++)
        {
            for (size_t j = 0; j < 6; j++) {
                //std::cout << response[i][j] << " " << std::endl;
                //sv.push_back(to_string(response[i][j]));
                glist = glist + to_string(response[i][j])+",";
            }
        }
        s = env->NewStringUTF(glist.c_str());
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }
    return s;
}

JNIEXPORT jstring JNICALL Java_myBooks_BorrowedBooks(JNIEnv* env, jobject thisObj, jstring username) {
    int uid;

    try
    {
        const char* name1 = env->GetStringUTFChars(username, NULL);

        string usname = name1;

        pqxx::connection connectionObject("host = localhost port = 5432 dbname = postgres user = postgres password = caNarain@2002");

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("SELECT * FROM user_login");

        for (size_t i = 0; i < response.size(); i++)
        {
            if (usname.compare(response[i][1].as<string>()) == 0)
            {
                uid = response[i][0].as<int>();
            }
        }
    }
    catch (const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
    }
    string glist;
    jstring s=NULL;

    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection connectionObject(connectionString.c_str());

        pqxx::work worker(connectionObject);

        pqxx::result response = worker.exec("Select a.book_id,a.book_name,a.author_name,a.edition,a.publisher,b.return_date,b.borrow_date FROM BOOKS a join user_data b on a.book_id=b.book_id WHERE b.user_id= " + std::to_string(uid) + ";");

        for (size_t i = 0; i < response.size(); i++)
        {
            for (size_t j = 0; j < 7; j++) {
                //std::cout << response[i][j] << " " << std::endl;
                //sv.push_back(to_string(response[i][j]));
                glist = glist + to_string(response[i][j]) + ",";
            }
        }
        s = env->NewStringUTF(glist.c_str());
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
    return s;
}

JNIEXPORT jstring JNICALL Java_viewAll_searchBook(JNIEnv* env, jobject thisObj, jstring bname)
{
    const char* bname1 = env->GetStringUTFChars(bname, NULL);

    std::string bookname = bname1;

    string glist;
    jstring s = NULL;

    try {
        std::string connectionString = "host=localhost port=5432 dbname=postgres user=postgres password =caNarain@2002";

        pqxx::connection connectionObject(connectionString.c_str());

        pqxx::work worker(connectionObject);
        if (connectionObject.is_open()) {
            std::cout << "Opened database successfully: " << connectionObject.dbname() << endl;
        }
        else {
            std::cout << "Can't open database" << endl;
        }

        pqxx::result response = worker.exec("SELECT * FROM BOOKS WHERE BOOK_NAME LIKE '"+bookname+"%' ");
        //cout<<"Size:" << response.size()<<endl;
        for (size_t i = 0; i < response.size(); i++)
        {
            for (size_t j = 0; j < 6; j++) {
                //std::cout << response[i][j] << " " << std::endl;
                //sv.push_back(to_string(response[i][j]));
                //cout << "vanakam1";
                glist = glist + to_string(response[i][j]) + ",";
            }
            glist = glist + '\n';
        }
        //cout << "vanakam2";
        s = env->NewStringUTF(glist.c_str());
    }
    catch (const std::exception& e) {
        cerr << e.what() << std::endl;
    }
    cout << "vanakam3";
    cout << glist<< "Narain";
    return s;
}

int main() {
    JNIEXPORT jboolean JNICALL Java_login_checkadmin (JNIEnv* env, jobject thisObj, jstring usname, jstring pswd);
    JNIEXPORT jboolean JNICALL Java_login_checkuser (JNIEnv* env, jobject thisObj, jstring usname, jstring pswd);    
    JNIEXPORT void JNICALL Java_update_updateBook(JNIEnv * env, jobject thisObj, jint bid, jint q);
    JNIEXPORT void JNICALL Java_add_addBook(JNIEnv * env, jobject thisObj, jint bid, jstring name, jstring author, jstring edd, jstring pub, jint q);
    JNIEXPORT void JNICALL Java_delete_deleteBook(JNIEnv * env, jobject thisObj, jint bid);
    JNIEXPORT void JNICALL Java_borrow_borrowBook(JNIEnv * env, jobject thisObj, jint bid, jstring username);
    JNIEXPORT void JNICALL Java_returnback_returnBook(JNIEnv * env, jobject thisObj, jint bid, jstring username);
    JNIEXPORT jstring JNICALL Java_viewAll_viewBooks(JNIEnv * env, jobject thisObj);
    JNIEXPORT jstring JNICALL Java_myBooks_BorrowedBooks(JNIEnv * env, jobject thisObj, jstring username);
    JNIEXPORT jstring JNICALL Java_search_searchBook(JNIEnv * env, jobject thisObj, jint bid);
}

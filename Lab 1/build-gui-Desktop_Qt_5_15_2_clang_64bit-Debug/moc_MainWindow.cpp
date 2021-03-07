/****************************************************************************
** Meta object code from reading C++ file 'MainWindow.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.15.2)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include <memory>
#include "../gui/MainWindow.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'MainWindow.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.15.2. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_MainWindow_t {
    QByteArrayData data[17];
    char stringdata0[320];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_MainWindow_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_MainWindow_t qt_meta_stringdata_MainWindow = {
    {
QT_MOC_LITERAL(0, 0, 10), // "MainWindow"
QT_MOC_LITERAL(1, 11, 14), // "functionChosen"
QT_MOC_LITERAL(2, 26, 0), // ""
QT_MOC_LITERAL(3, 27, 5), // "index"
QT_MOC_LITERAL(4, 33, 12), // "methodChosen"
QT_MOC_LITERAL(5, 46, 24), // "findMinPushButtonPressed"
QT_MOC_LITERAL(6, 71, 25), // "findMinPushButtonReleased"
QT_MOC_LITERAL(7, 97, 24), // "findMinPushButtonClicked"
QT_MOC_LITERAL(8, 122, 21), // "backPushButtonPressed"
QT_MOC_LITERAL(9, 144, 22), // "backPushButtonReleased"
QT_MOC_LITERAL(10, 167, 21), // "backPushButtonClicked"
QT_MOC_LITERAL(11, 189, 22), // "resetPushButtonPressed"
QT_MOC_LITERAL(12, 212, 23), // "resetPushButtonReleased"
QT_MOC_LITERAL(13, 236, 22), // "resetPushButtonClicked"
QT_MOC_LITERAL(14, 259, 21), // "listWidgetItemClicked"
QT_MOC_LITERAL(15, 281, 16), // "QListWidgetItem*"
QT_MOC_LITERAL(16, 298, 21) // "realtimeShowIteration"

    },
    "MainWindow\0functionChosen\0\0index\0"
    "methodChosen\0findMinPushButtonPressed\0"
    "findMinPushButtonReleased\0"
    "findMinPushButtonClicked\0backPushButtonPressed\0"
    "backPushButtonReleased\0backPushButtonClicked\0"
    "resetPushButtonPressed\0resetPushButtonReleased\0"
    "resetPushButtonClicked\0listWidgetItemClicked\0"
    "QListWidgetItem*\0realtimeShowIteration"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_MainWindow[] = {

 // content:
       8,       // revision
       0,       // classname
       0,    0, // classinfo
      13,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: name, argc, parameters, tag, flags
       1,    1,   79,    2, 0x0a /* Public */,
       4,    1,   82,    2, 0x0a /* Public */,
       5,    0,   85,    2, 0x0a /* Public */,
       6,    0,   86,    2, 0x0a /* Public */,
       7,    0,   87,    2, 0x0a /* Public */,
       8,    0,   88,    2, 0x0a /* Public */,
       9,    0,   89,    2, 0x0a /* Public */,
      10,    0,   90,    2, 0x0a /* Public */,
      11,    0,   91,    2, 0x0a /* Public */,
      12,    0,   92,    2, 0x0a /* Public */,
      13,    0,   93,    2, 0x0a /* Public */,
      14,    1,   94,    2, 0x0a /* Public */,
      16,    0,   97,    2, 0x0a /* Public */,

 // slots: parameters
    QMetaType::Void, QMetaType::Int,    3,
    QMetaType::Void, QMetaType::Int,    3,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, 0x80000000 | 15,    2,
    QMetaType::Void,

       0        // eod
};

void MainWindow::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        auto *_t = static_cast<MainWindow *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->functionChosen((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 1: _t->methodChosen((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 2: _t->findMinPushButtonPressed(); break;
        case 3: _t->findMinPushButtonReleased(); break;
        case 4: _t->findMinPushButtonClicked(); break;
        case 5: _t->backPushButtonPressed(); break;
        case 6: _t->backPushButtonReleased(); break;
        case 7: _t->backPushButtonClicked(); break;
        case 8: _t->resetPushButtonPressed(); break;
        case 9: _t->resetPushButtonReleased(); break;
        case 10: _t->resetPushButtonClicked(); break;
        case 11: _t->listWidgetItemClicked((*reinterpret_cast< QListWidgetItem*(*)>(_a[1]))); break;
        case 12: _t->realtimeShowIteration(); break;
        default: ;
        }
    }
}

QT_INIT_METAOBJECT const QMetaObject MainWindow::staticMetaObject = { {
    QMetaObject::SuperData::link<QMainWindow::staticMetaObject>(),
    qt_meta_stringdata_MainWindow.data,
    qt_meta_data_MainWindow,
    qt_static_metacall,
    nullptr,
    nullptr
} };


const QMetaObject *MainWindow::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *MainWindow::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_MainWindow.stringdata0))
        return static_cast<void*>(this);
    return QMainWindow::qt_metacast(_clname);
}

int MainWindow::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QMainWindow::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 13)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 13;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 13)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 13;
    }
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE

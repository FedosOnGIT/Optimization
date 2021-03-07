#include "logger.h"
#include <filesystem>

// Папка, где будут логи
static const char *LOGS_PATH = "./logs/";

logger::logger(std::ostream *stream, unsigned int precision)
        : out(stream), is_owner(false) {
    if (out != nullptr) {
        out_init(precision);
    }
}

logger::logger(std::string const &file)
        : is_owner(true) {
    std::filesystem::create_directories(LOGS_PATH);
    out = new std::ofstream(LOGS_PATH + file);
    out_init();
}

logger::logger(logger &&other) noexcept
        : out(other.out), is_owner(other.is_owner) {
    other.out = nullptr;
    other.is_owner = false;
}

logger::~logger() {
    close();
}

logger &logger::operator=(logger &&other)  noexcept {
    close();
    out = other.out;
    is_owner = other.is_owner;
    other.out = nullptr;
    other.is_owner = false;
    return *this;
}

void logger::out_init(unsigned int precision) {
    out->setf(std::iostream::fixed);
    out->precision(precision);
}

void logger::close() {
    if (is_owner) {
        delete out;
        out = nullptr;
    }
}

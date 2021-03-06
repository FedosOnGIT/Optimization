#include "logger.h"
#include <filesystem>

// Папка, где будут логи
static const char *LOGS_PATH = "./logs/";

logger::logger(std::ostream *stream)
        : out(stream), is_owner(false) {
    out_init();
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
    if (is_owner)
        delete out;
}

void logger::out_init() {
    out->setf(std::iostream::fixed);
    out->precision(15);
}

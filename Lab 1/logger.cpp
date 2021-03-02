#include "logger.h"
#include <filesystem>
#include <variant>

// Папка, где будут логи
static const std::string LOGS_PATH = "./logs/";

logger::logger(std::ostream& stream)
  : out(&stream)
{}

logger::logger(std::string const& file) {
  std::filesystem::create_directories(LOGS_PATH);
  out = std::make_unique<std::ofstream>(LOGS_PATH + file);
  out->setf(std::iostream::fixed);
  out->precision(15);
}

void logger::spec_print() {}

template <typename Current> void logger::spec_print(Current current) {
  *out << current;
}

template <typename Current, typename... Rest>
void logger::spec_print(Current current, Rest... rest) {
  spec_print(current);
  *out << '\t';
  spec_print(rest...);
}

template <typename... Args> logger& logger::print(Args... args) {
  spec_print(args...);
  *out << '\t';
  return *this;
}

template <typename... Args> logger& logger::println(Args... args) {
  spec_print(args...);
  *out << '\n';
  return *this;
}

template <typename T> logger& operator<<(logger& lgg, T const& x) {
  *lgg.out << x;
  return lgg;
}

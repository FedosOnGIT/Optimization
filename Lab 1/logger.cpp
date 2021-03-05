#include "logger.h"
#include <filesystem>

// Папка, где будут логи
static const char* LOGS_PATH = "./logs/";

logger::logger(std::ostream* stream)
  : out(stream), is_owner(false)
{}

logger::logger(std::string const& file)
  : is_owner(true)
{
  std::filesystem::create_directories(LOGS_PATH);
  out = new std::ofstream(LOGS_PATH + file);
  out->setf(std::iostream::fixed);
  out->precision(15);
}

logger::~logger() {
  if (is_owner)
    delete out;
}

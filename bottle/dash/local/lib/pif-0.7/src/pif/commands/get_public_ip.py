from __future__ import print_function

__title__ = 'pif.commands.get_public_ip'
__author__ = 'Artur Barseghyan'
__copyright__ = 'Copyright (c) 2013 Artur Barseghyan'
__license__ = 'GPL 2.0/LGPL 2.1'
__all__ = ('main',)

import argparse

from pif.utils import get_public_ip

def main():
    """
    Get public IP.

    :example:

    $ python src/pif/get_public_ip.py -pc user
    """
    parser = argparse.ArgumentParser(description="""
    Get public IP.
    """)

    parser.add_argument("-c", "--checker", dest="preferred_checker", type=str, help="`preferred_checker` value", \
                        metavar="PREFERRED_CHECKER")
    parser.add_argument("-v", "--verbose", dest="verbose", type=str, help="`verbose` value", \
                        metavar="VERBOSE")
    args = parser.parse_args()

    kwargs = {}

    if args.preferred_checker:
        kwargs.update({'preferred_checker': args.preferred_checker})

    verbose = None

    try:
        verbose = bool(int(args.preferred_checker))
        kwargs.update({'verbose': verbose})
    except:
        pass

    try:
        public_ip = get_public_ip(**kwargs)
        print(public_ip)
    except Exception as e:
        print(e)


if __name__ == "__main__":
    main()
